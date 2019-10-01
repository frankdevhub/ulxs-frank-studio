package com.ulxsfrank.business.validator.impl;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title:@ClassName PatternValidator.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/4 20:59
 * @Version: 1.0
 */
public abstract class PatternValidator<A extends Annotation> implements ConstraintValidator<A, String> {
    private String pattern;

    public PatternValidator(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void initialize(A constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return Boolean.TRUE.booleanValue();
        }
        Assert.notNull(this.pattern);
        boolean flag = Boolean.FALSE.booleanValue();
        Pattern p1 = null;
        Matcher m = null;
        p1 = Pattern.compile(this.pattern);
        m = p1.matcher(value);
        flag = m.matches();
        return flag;
    }

}
