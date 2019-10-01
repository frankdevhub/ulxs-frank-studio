package com.ulxsfrank.business.utils;

import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.entity.FeedBackEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>Title:@ClassName FeedBackMailSender.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 22:45
 * @Version: 1.0
 */
public class FeedBackMailSender {

    private static final String MAILOG = "maillog";
    private String templateFileName;
    private File templateDirectory;
    private FeedBackEntity data;
    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedBackMailSender.class);

    public static FeedBackMailSender newSender() {
        return new FeedBackMailSender();
    }

    public FeedBackMailSender setData(FeedBackEntity data) {
        this.data = data;
        return this;
    }

    public FeedBackMailSender setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
        return this;
    }

    public FeedBackMailSender setTemplateDirectoryPath(String path) throws Exception {
        this.templateDirectory = new File(path);
        if (!templateDirectory.exists())
            throw new Exception("[" + templateDirectory.getAbsolutePath() + "]:template directory does not exist!");
        if (Boolean.FALSE.equals(templateDirectory.isDirectory()))
            throw new Exception("template directory should be a directory!");
        return this;
    }

	public void sendTemplateMail(String templateMessage) throws MessagingException {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        senderImpl.setHost("smtp.exmail.qq.com");
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        senderImpl.setJavaMailProperties(prop);
        senderImpl.setUsername("");
        senderImpl.setPassword("");

        MimeMessage mimeMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom("service@meixiangtech.site");
        messageHelper.setTo(new String[]{"", ""});
        messageHelper.setSubject(String.format("New Contact Request From Official Web Page-[%s]", new Date().getTime()));
        messageHelper.setText(templateMessage, true);
        senderImpl.send(mimeMessage);
    }


    public String loadHtml() {

        // version:2.3.23
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        try {
            config.setDirectoryForTemplateLoading(this.templateDirectory);
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setClassicCompatible(true);

            // get template
            Template template = config.getTemplate(this.templateFileName);
            StringWriter writer = new StringWriter();
            // inject Data Map
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put(MAILOG, data);
            template.process(dataMap, writer);
            String html = writer.toString();
            System.out.println(html);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("load file failed: %s", e.getMessage()));

        }
    }


}
