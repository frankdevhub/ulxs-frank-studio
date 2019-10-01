package com.ulxsfrank.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
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
	public String toPaymentPage() {
		LOGGER.begin().headerAction(MessageMethod.GET).info("navigate to payment service page.");
		return "payment";
	}
}
