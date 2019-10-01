package com.ulxsfrank.business.data;

/**
 * <p>Title:@ClassName Constants.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/19 5:11
 * @Version: 1.0
 */
public class Constants {
    public static final String SERVER_IP = "39.98.246.50";
    public static final String SEND_FEEDBACK_MAIL_SUCCESS = "渠道意见反馈邮件发送成功";
    public static final String FEEDBACK_MAIL_OUTLIMITED = "渠道意见邮件超出单日限制";
    public static final String INVALID_IP = "非法请求,无效IP";
    public static final String INVALID_FREQUENCY = "请求频率超出限制";

    public static final Integer MAIL_TYPE_SUBSCRIBE = 1; //首页邮件订阅
    public static final Integer MAIL_TYPE_CONTACT = 2; //联系页面邮件推送

}
