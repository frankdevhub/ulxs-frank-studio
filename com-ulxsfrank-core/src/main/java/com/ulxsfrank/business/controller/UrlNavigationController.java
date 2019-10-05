package com.ulxsfrank.business.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ulxsfrank.business.data.Constants;
import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.exception.BusinessException;
import com.ulxsfrank.business.message.MessageMethod;

/**
 * @ClassName: UrlNavigationController
 * @author: frankdevhub@gmail.com
 * @date: 2019年10月1日 下午7:59:00
 * 
 * @Copyright: 2019 www.frankdevhub.site Inc. All rights reserved.
 */

@Controller
@RequestMapping("/")
public class UrlNavigationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlNavigationController.class);

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndexPage() {
		{
			LOGGER.begin().headerAction(MessageMethod.GET).info("navigate to payment index page.");
			return "index";
		}
	}

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public ModelAndView toPaymentPage(HttpServletRequest request)
			throws ParseException, IOException, BusinessException {
		LOGGER.begin().headerAction(MessageMethod.GET).info("navigate to payment service page.");

		String code = request.getParameter("code");

		LOGGER.begin().headerAction(MessageMethod.EVENT).info("navigate to payment page and get access token");
		StringBuffer params = new StringBuffer();
		params.append("appid=" + Constants.WX_APP_ID + "").append("&").append("code=" + code + "").append("&")
				.append("secret=" + Constants.WX_APP_SECRET + "").append("&")
				.append("grant_type=" + Constants.WX_GRANT_TYPE_AUTH + "");

		System.out.println(String.format("params string:[%s]", params.toString()));
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getAccessToken = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token" + "?" + params);
		CloseableHttpResponse response;

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
		getAccessToken.setConfig(requestConfig);
		response = httpClient.execute(getAccessToken);

		HttpEntity responseEntity = response.getEntity();
		String responseText = EntityUtils.toString(responseEntity);
		System.out.println(String.format("response[get_access_token]:[%s]", responseText));

		JSONObject responseObject = JSON.parseObject(responseText);
		String accessToken = null;
		String openId = null;
		if ((null != responseObject.getString("access_token") && (null != responseObject.getString("openid")))) {
			accessToken = responseObject.getString("access_token").toString();
			openId = responseObject.getString("openid").toString();
		} else {
			String error = responseObject.getString("errmsg");
			throw new BusinessException(error);
		}

		httpClient = HttpClientBuilder.create().build();
		params = new StringBuffer();
		params.append("access_token=" + accessToken + "").append("&").append("openid=" + openId + "").append("&")
				.append("lang=zh_CN");
		HttpGet getUserInfo = new HttpGet("https://api.weixin.qq.com/sns/userinfo" + "?" + params);
		response = httpClient.execute(getUserInfo);

		responseEntity = response.getEntity();
		responseText = EntityUtils.toString(responseEntity);
		System.out.println(String.format("response[user_info]:[%s]", responseText));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/payment");
		modelAndView.addObject("accessToken", accessToken);
		modelAndView.addObject("openId", openId);

		return modelAndView;
	}
}
