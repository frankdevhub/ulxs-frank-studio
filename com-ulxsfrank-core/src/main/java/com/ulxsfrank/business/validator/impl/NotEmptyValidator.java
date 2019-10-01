package com.ulxsfrank.business.validator.impl;

import com.ulxsfrank.business.validator.constraint.NotEmpty;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>Title:@ClassName NotEmptyValidator.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/4 20:54
 * @Version: 1.0
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

    @Override
    public void initialize(NotEmpty constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(value);
    }
}
