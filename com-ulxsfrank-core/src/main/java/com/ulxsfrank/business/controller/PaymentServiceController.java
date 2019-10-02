package com.ulxsfrank.business.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
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

	private String getSignature(String openId) throws Exception {
		String wxNonceStr = WXPayUtil.generateNonceStr();
		System.out.println(wxNonceStr);
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("appid", Constants.WX_APP_ID);
		paraMap.put("body", "高级服装设计订单");
		paraMap.put("mch_id", Constants.WX_MCH_ID);
		paraMap.put("nonce_str", wxNonceStr);
		paraMap.put("openid", openId);
		paraMap.put("out_trade_no", "3438118099812");
		paraMap.put("spbill_create_ip", "58.34.44.32");

		paraMap.put("trade_type", "JSAPI");
		paraMap.put("notify_url", "www.jilu-samplestudio.com/payment/callback");
		String sign = WXPayUtil.generateSignature(paraMap, Constants.WX_PATERNER_KEY);
		paraMap.put("sign", sign);
		String xml = WXPayUtil.mapToXml(paraMap);
		System.out.println(xml);
		return null;
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

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
		httpGet.setConfig(requestConfig);
		response = httpClient.execute(httpGet);

		HttpEntity responseEntity = response.getEntity();
		String responseText = EntityUtils.toString(responseEntity);
		System.out.println(String.format("response:[%s]", responseText));

		JSONObject responseObject = JSON.parseObject(responseText);
		if ((null != responseObject.get("access_token")) && (null != responseObject.get("openid"))) {
			resMap.put("access_token", responseObject.getString("access_token").toString());
			resMap.put("openid", responseObject.getString("openid").toString());
		} else {
			String error = responseObject.getString("errmsg");
			throw new BusinessException(error);
		}

		return resMap;
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public Response<Map<String, String>> prePayService(@RequestBody String code) {
		try {
			LOGGER.begin().headerAction(MessageMethod.GET).info("start prepay service.");
			Map<String, String> resMap = getWxAccessToken(code);
			String accessToken = resMap.get("access_token");
			String openId = resMap.get("openid");
			return new Response<Map<String, String>>().setData(null).success();
		} catch (Exception e) {
			return new Response<Map<String, String>>().setData(null).failed();
		}
	}

	public static void main(String[] args) throws Exception {
		new PaymentServiceController().getWxAccessToken("081Tslit1s3aSf0utwht1fpiit1Tsli6");

	}

}
