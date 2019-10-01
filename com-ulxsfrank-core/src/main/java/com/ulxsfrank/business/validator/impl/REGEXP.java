package com.ulxsfrank.business.validator.impl;

/**
 * <p>Title:@ClassName REGEXP.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/4 20:45
 * @Version: 1.0
 */
public class REGEXP {

    public static final String MOBILE = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$";
    public static final String WECHAT = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
    public static final String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    public static final String QQ = "[1-9][0-9]{4,14}";
    public static final String TEL = "^(0\\d{2,3}-\\d{7,8}(-\\d{3,5}){0,1})|(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})$";

    public static final String URL_IMAGE = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?(.jpg|.png)";
    public static final String URL_HTML = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?(.html)";

    public final static String getRegexField(String fieldName) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class clazz = REGEXP.class;
        String value = (String) clazz.getDeclaredField(fieldName).get(clazz.newInstance());
        return value;
    }

}
