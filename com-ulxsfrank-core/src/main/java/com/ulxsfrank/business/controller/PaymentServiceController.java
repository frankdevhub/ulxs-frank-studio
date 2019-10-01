package com.ulxsfrank.business.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;

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
}
