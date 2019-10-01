package com.ulxsfrank.business.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Title:@ClassName SpringUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/18 18:26
 * @Version: 1.0
 */

@Component
public class SpringUtils implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T getBean(@SuppressWarnings("rawtypes") Class T) {

        return (T) applicationContext.getBean(T);
    }

    public static <T> T getBean(String name) {

        return (T) applicationContext.getBean(name);
    }
}

