package com.example.annotation;


import com.example.annotation.impl.PositionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositionValidator.class)
public @interface Position {

    String message() default "职位输入错误";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
