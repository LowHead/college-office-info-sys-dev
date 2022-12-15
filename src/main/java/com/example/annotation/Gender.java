package com.example.annotation;

import com.example.annotation.impl.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface Gender {

    String message() default "性别输入错误";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
