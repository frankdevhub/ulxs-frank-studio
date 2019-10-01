package com.ulxsfrank.business.entity;

import java.util.Properties;

/**
 * <p>Title:@ClassName ConfigProperties.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/2 4:35
 * @Version: 1.0
 */
public class ConfigProperties extends Properties {

    public synchronized ConfigProperties setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    public synchronized ConfigProperties put(Object key, Object value) {
        super.put(key, value);
        return this;
    }


}
