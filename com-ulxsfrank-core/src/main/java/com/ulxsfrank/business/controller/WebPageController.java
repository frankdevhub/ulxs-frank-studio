package com.ulxsfrank.business.controller;

import com.ulxsfrank.business.configuration.SnowFlakeGenerator;
import com.ulxsfrank.business.data.Constants;
import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.data.rest.result.Response;
import com.ulxsfrank.business.entity.FeedBackEntity;
import com.ulxsfrank.business.entity.MailActionLogEntity;
import com.ulxsfrank.business.entity.PageLogEntity;
import com.ulxsfrank.business.message.MessageMethod;
import com.ulxsfrank.business.repository.FeedBackMailRepository;
import com.ulxsfrank.business.repository.MailLogRepository;
import com.ulxsfrank.business.repository.PageLogRepository;
import com.ulxsfrank.business.utils.FeedBackMailSender;
import com.ulxsfrank.business.utils.SpringUtils;
import com.ulxsfrank.business.utils.TencentIpLocator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title:@ClassName WebPageController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/18 18:29
 * @Version: 1.0
 */
@RestController
@RequestMapping("/web/home")
public class WebPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebPageController.class);

    private PageLogRepository getPageRepo() {
        return SpringUtils.getBean(PageLogRepository.class);
    }

    private MailLogRepository getMailLogRepo() {
        return SpringUtils.getBean(MailLogRepository.class);
    }

    private FeedBackMailRepository getFeedBackMailRepo() {
        return SpringUtils.getBean(FeedBackMailRepository.class);
    }

    public static Long[] getCurrentDayTimestamp() {
        Long[] timestamps = new Long[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        timestamps[0] = start.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.SECOND, -1);
        Date end = calendar.getTime();
        timestamps[1] = end.getTime();

        System.out.println(String.format("current day start at:[%s]", timestamps[0]));
        System.out.println(String.format("current day end at:[%s]", timestamps[1]));

        return timestamps;
    }

    private Boolean validateMailLimit(Long[] timestamps) throws Exception {
        Integer count = getMailLogRepo().getContactMailsBetweenTimestamps(timestamps);
        if (count > 150)
            throw new Exception("mail service out of limit today.");
        return Boolean.TRUE;
    }


    private Boolean validateMailLimitByIp(Long[] timestamps, String ip) throws Exception {
        Integer count = getMailLogRepo().getContactMailsBetweenTimestampsByIp(timestamps, ip);
        if (count >= 3)
            throw new Exception("mail service out of limit on current ip.");
        return Boolean.TRUE;
    }

    @Transactional
    @RequestMapping(value = "/feedBack/mail", method = RequestMethod.POST)
    public Response<Boolean> feedBackMail(@Validated @RequestBody FeedBackEntity feedBackEntity, HttpServletRequest request) {
        try {
            String ip = CommonInterceptor.getRealIp(request);
            Assert.notNull(ip);
            //create feedback mail log
            MailActionLogEntity mailLog = new MailActionLogEntity();
            mailLog.setId(new SnowFlakeGenerator().generateKey());
            mailLog.setDate(new Date().getTime());
            mailLog.setType(Constants.MAIL_TYPE_CONTACT);
            mailLog.setIp(ip);

            getMailLogRepo().insertMailLog(mailLog);

            Integer type = feedBackEntity.getType();
            if (!type.equals(Constants.MAIL_TYPE_CONTACT))
                throw new Exception("invalid mail type.");

            feedBackEntity.setId(new SnowFlakeGenerator().generateKey());
            feedBackEntity.setAgreeProtocol(Boolean.TRUE);
            Long current = new Date().getTime();
            //validate mail service limit on that day (less than 200)
            Long[] timestamps = getCurrentDayTimestamp();
            validateMailLimit(timestamps);
            //each ip can send mail no more than 3 times
            validateMailLimitByIp(timestamps, ip);
            feedBackEntity.setDate(current);
            feedBackEntity.setAgreeProtocol(Boolean.TRUE);
            feedBackEntity.setRemoteHost(ip);

            //insert log data and mail detail data
            getFeedBackMailRepo().insertFeedbackMail(feedBackEntity);

            //sendFeedBackMail
            FeedBackMailSender sender = new FeedBackMailSender();
            String mailText = sender.setData(feedBackEntity).setTemplateDirectoryPath("src/main/resources")
                    .setTemplateFileName("mail-contact.html").loadHtml();

            sender.sendTemplateMail(mailText);

            return new Response<Boolean>().setData(Boolean.TRUE).setMessage("subscribe mail send success.").success();
        } catch (Exception e) {

            return new Response<Boolean>().setData(Boolean.FALSE).setMessage(e.getMessage()).failed();
        }
    }

    @Transactional
    @RequestMapping(value = "/page/log", method = RequestMethod.POST)
    public Response<Boolean> pageViewLog(@Validated @RequestBody PageLogEntity entity, HttpServletRequest request) {
        try {
            String ip = CommonInterceptor.getRealIp(request);
            entity.setIp(ip);
            Assert.notNull(ip);
            LOGGER.begin().headerAction(MessageMethod.POST).info(String.format("[%s] insert page log.", ip));
            entity.setId(new SnowFlakeGenerator().generateKey());
            entity.setDate(new Date().getTime());

            String[] locArray = TencentIpLocator.getIpLocation(ip);
            entity.setLat(locArray[0]);
            entity.setLng(locArray[1]);
            String location = new StringBuffer().append(locArray[0]).append(",").append(locArray[1]).toString();
            entity.setAddress(TencentIpLocator.getAddress(location));
            PageLogRepository repository = getPageRepo();
            repository.insertPageLog(entity);
            return new Response<Boolean>().setData(Boolean.TRUE).setMessage("page log insert success.").success();
        } catch (Exception e) {
            LOGGER.begin().headerAction(MessageMethod.ERROR).error(e.getMessage());
            return new Response<Boolean>().setData(Boolean.FALSE).setMessage(e.getMessage()).failed();
        }
    }

}