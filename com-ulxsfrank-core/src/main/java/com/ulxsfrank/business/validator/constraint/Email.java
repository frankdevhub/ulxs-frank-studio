package com.ulxsfrank.business.validator.constraint;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface Email {

    String message() default "字段必须是邮件格式";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
