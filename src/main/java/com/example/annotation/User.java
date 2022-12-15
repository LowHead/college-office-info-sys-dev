package com.example.annotation;

import com.example.annotation.impl.PositionValidator;
import com.example.annotation.impl.UserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserValidator.class)
public @interface User {
    String message() default "用户已存在！";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
