package com.ulxsfrank.business.validator.constraint;

import com.ulxsfrank.business.validator.impl.NotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <p>Title:@ClassName NotEmpty.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/8/4 20:50
 * @Version: 1.0
 */


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NotEmptyValidator.class})
public @interface NotEmpty {
    String message() default "字段不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


