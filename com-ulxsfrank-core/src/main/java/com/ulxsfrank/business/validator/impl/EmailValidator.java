package com.ulxsfrank.business.validator.impl;

import com.ulxsfrank.business.validator.constraint.Email;

import java.lang.annotation.Annotation;

/**
 * <p>Title:@ClassName EmailValidator.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/4 21:08
 * @Version: 1.0
 */
public class EmailValidator<A extends Annotation> extends PatternValidator<Email> {

    public EmailValidator(String pattern) {
        super(REGEXP.EMAIL);
    }
}
