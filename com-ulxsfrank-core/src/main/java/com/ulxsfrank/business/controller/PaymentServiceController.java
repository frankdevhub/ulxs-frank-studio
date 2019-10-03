package com.ulxsfrank.business.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConstants.SignType;
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

	@SuppressWarnings("deprecation")
	private Map<String, String> getSignature(Map<String, String> map) throws Exception {
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
		paraMap.put("spbill_create_ip", "58.34.44.32");
		paraMap.put("total_fee", "1");

		paraMap.put("trade_type", "JSAPI");
		paraMap.put("notify_url", "jilu-samplestudio.com/payment/callback");
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

		payMap.put("appid", Constants.WX_APP_ID);
		payMap.put("nonceStr", wxNonceStr);
		payMap.put("signType", "HMACSHA256");
		payMap.put("package", "prepay_id=" + prepPayId);
		String paySign = WXPayUtil.generateSignature(payMap, Constants.WX_PATERNER_KEY, SignType.HMACSHA256);
		System.out.println(String.format("generate sign as:[%s]", paySign));

		payMap.put("paySign", paySign);

		return payMap;
	}

	private Map<String, String> getWxAccessToken(String code)
			throws BusinessException, ClientProtocolException, IOException {

		LOGGER.begin().headerAction(MessageMethod.EVENT).info("start do getWxAccessToken.");
		if (StringUtils.isEmpty(code))
			throw new BusinessException("wx code should not be empty.");

		StringBuffer params = new StringBuffer();
		params.append("appid=" + Constants.WX_APP_ID + "");
		params.append("&");
		params.append("secret=" + Constants.WX_APP_SECRET + "");
		params.append("&");
		params.append("code=" + code + "");
		params.append("&");
		params.append("grant_type=" + Constants.WX_GRANT_TYPE + "");

		System.out.println(String.format("params string:[%s]", params.toString()));
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token" + "?" + params);
		CloseableHttpResponse response;

		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("access_token", null);
		resMap.put("openid", null);
		resMap.put("code", code);

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
		httpGet.setConfig(requestConfig);
		response = httpClient.execute(httpGet);

		HttpEntity responseEntity = response.getEntity();
		String responseText = EntityUtils.toString(responseEntity);
		System.out.println(String.format("response[get_access_token]:[%s]", responseText));

		JSONObject responseObject = JSON.parseObject(responseText);
		if ((null != responseObject.getString("access_token")) && (null != responseObject.getString("openid"))) {
			resMap.put("access_token", responseObject.getString("access_token").toString());
			resMap.put("openid", responseObject.getString("openid").toString());
		} else {
			String error = responseObject.getString("errmsg");
			throw new BusinessException(error);
		}

		return resMap;
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public Response<Map<String, String>> prePayService(@RequestParam(name = "code") String code) {
		try {
			LOGGER.begin().headerAction(MessageMethod.GET).info("start prepay service.");

			Map<String, String> tokenResponseMap = getWxAccessToken(code);
			Map<String, String> signResponseMap = getSignature(tokenResponseMap);
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

			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
			is.close();
			return new Response<Boolean>().setData(Boolean.TRUE).setMessage("success").success();

		} catch (Exception e) {
			return new Response<Boolean>().setData(Boolean.FALSE).setMessage(e.getMessage()).failed();
		}

	}

}
