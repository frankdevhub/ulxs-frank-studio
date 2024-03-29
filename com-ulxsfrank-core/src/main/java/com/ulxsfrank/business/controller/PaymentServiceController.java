package com.ulxsfrank.business.controller;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxpay.sdk.WXPayUtil;
import com.ulxsfrank.business.configuration.SnowFlakeIdWorker;
import com.ulxsfrank.business.data.Constants;
import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.data.rest.result.Response;
import com.ulxsfrank.business.exception.BusinessException;
import com.ulxsfrank.business.message.MessageMethod;

/**
 * @ClassName: PaymentServiceController
 * @author: frankdevhub@gmail.com
 * @date: 2019年10月1日 下午4:10:49
 * 
 * @Copyright: 2019 www.frankdevhub.site Inc. All rights reserved.
 */

@RestController
@RequestMapping("/payment")
public class PaymentServiceController {

	private Logger LOGGER = LoggerFactory.getLogger(PaymentServiceController.class);

	private Map<String, String> getSignature(HttpServletRequest request, Map<String, String> map, String currency)
			throws Exception {
		LOGGER.begin().headerAction(MessageMethod.EVENT).info("start do getSignature.");

		String openId = map.get("openid");

		String wxNonceStr = WXPayUtil.generateNonceStr();
		String tradeNumber = Long.toString(new SnowFlakeIdWorker().nextId());
		System.out.println(String.format("using nonce string:[%s]", wxNonceStr));
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("appid", Constants.WX_APP_ID);
		paraMap.put("openid", openId);
		paraMap.put("body", "test-order");
		paraMap.put("mch_id", Constants.WX_MCH_ID);
		paraMap.put("nonce_str", wxNonceStr);
		paraMap.put("out_trade_no", tradeNumber);
		paraMap.put("spbill_create_ip", CommonInterceptor.getRealIp(request));// TODO
		paraMap.put("total_fee", currency);

		paraMap.put("trade_type", "JSAPI");
		paraMap.put("notify_url", "http://jilu-samplestudio.com/payment/callback");// TODO
																					// jilu-samplestudio.com/payment/callback
		String sign = WXPayUtil.generateSignature(paraMap, Constants.WX_PATERNER_KEY);
		System.out.println(String.format("generate sign as:[%s]", sign));

		paraMap.put("sign", sign);
		System.out.println(WXPayUtil.mapToXml(paraMap));
		return paraMap;
	}

	private Map<String, String> postOrderRequest(Map<String, String> paraMap) throws Exception {

		LOGGER.begin().headerAction(MessageMethod.EVENT).info("start do postOrderRequest.");

		String xml = WXPayUtil.mapToXml(paraMap);
		StringEntity bodyEntity = new StringEntity(xml, "UTF-8");
		String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(unifiedorderUrl);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setEntity(bodyEntity);

		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity responseEntity = response.getEntity();
		String responseText = EntityUtils.toString(responseEntity);
		System.out.println(String.format("response[pay_order_request]:[%s]", responseText));

		Map<String, String> resMap = WXPayUtil.xmlToMap(responseText);
		if (resMap.get("prepay_id").equals(null))
			throw new BusinessException("prepay_id cannot be null.");

		Map<String, String> payMap = new HashMap<String, String>();

		payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
		System.out.println(String.format("timestamp:[%s]", WXPayUtil.getCurrentTimestamp()));

		String wxNonceStr = WXPayUtil.generateNonceStr();
		String prepPayId = resMap.get("prepay_id");

		System.out.println(String.format("using wxnonce string:[%s]", wxNonceStr));
		System.out.println(String.format("using prepay id:[%s]", prepPayId));

		payMap.put("appId", Constants.WX_APP_ID);
		payMap.put("nonceStr", wxNonceStr);
		payMap.put("signType", "MD5");
		payMap.put("package", "prepay_id=" + prepPayId);
		String paySign = WXPayUtil.generateSignature(payMap, Constants.WX_PATERNER_KEY);// TODO
																						// HMACSHA256
		System.out.println(String.format("generate sign as:[%s]", paySign));

		payMap.put("paypackage", "prepay_id=" + prepPayId);
		payMap.put("paySign", paySign);

		return payMap;
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public Response<Map<String, String>> prePayService(@RequestParam(name = "accessToken") String accessToken,
			@RequestParam(name = "openId") String openId, @RequestParam(name = "currency") String currency,
			HttpServletRequest request) {
		try {
			LOGGER.begin().headerAction(MessageMethod.GET).info("start prepay service.");
			Map<String, String> signRequestMap = new HashMap<String, String>();
			signRequestMap.put("access_token", accessToken);
			signRequestMap.put("openid", openId);
			Map<String, String> signResponseMap = getSignature(request, signRequestMap, currency);
			Map<String, String> orderResponseMap = postOrderRequest(signResponseMap);

			return new Response<Map<String, String>>().setData(orderResponseMap).setMessage("success").success();
		} catch (Exception e) {
			return new Response<Map<String, String>>().setData(null).setMessage(e.getMessage()).failed();
		}
	}

	@RequestMapping(value = "/callback", method = RequestMethod.POST)
	public Response<Boolean> callBackPayService(HttpServletRequest request, HttpServletResponse response) {
		try {
			LOGGER.begin().headerAction(MessageMethod.GET).info("start prepay callback.");
			InputStream is = request.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String xml = writer.toString();

			System.out.println(String.format("[return_map]:[%s]", xml));

			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);
			// response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
			is.close();
			return new Response<Boolean>().setData(Boolean.TRUE).setMessage("success").success();

		} catch (Exception e) {
			return new Response<Boolean>().setData(Boolean.FALSE).setMessage(e.getMessage()).failed();
		}

	}

}
