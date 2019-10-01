package com.ulxsfrank.business.controller;

import com.ulxsfrank.business.data.Constants;
import com.ulxsfrank.business.data.logging.Logger;
import com.ulxsfrank.business.data.logging.LoggerFactory;
import com.ulxsfrank.business.exception.NoSuchPermissionException;
import com.ulxsfrank.business.message.MessageMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * <p>Title:@ClassName CommonInterceptor.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/18 19:02
 * @Version: 1.0
 */

@Component
public class CommonInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        LOGGER.begin().headerMethod(MessageMethod.EVENT).info("invoke preHandle");
        String remoteHost = getRealIp(httpServletRequest);
        if (StringUtils.isEmpty(remoteHost)) {
            LOGGER.begin().headerMethod(MessageMethod.ERROR)
                    .info(String.format("invalid remote host:[%s]", remoteHost));
            throw new NoSuchPermissionException(Constants.INVALID_IP);
        }

        String authorizedHostSingle = Constants.SERVER_IP;
       /* if (!authorizedHostSingle.equals(remoteHost)) {
            LOGGER.begin().headerMethod(MessageMethod.ERROR)
                    .info(String.format("invalid remote host:[%s]", remoteHost));
            throw new NoSuchPermissionException(Constants.INVALID_IP);
        }*/
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept");
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public static String getRealIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
                LOGGER.begin().headerMethod(MessageMethod.EVENT)
                        .info(String.format("ip@x-forwarded-for:[%s]", ip));
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        LOGGER.begin().headerMethod(MessageMethod.EVENT)
                .info(String.format("source ip:[%s]", ip));
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;

    }


}
