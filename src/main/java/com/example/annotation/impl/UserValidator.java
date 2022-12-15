package com.example.annotation.impl;
import com.example.annotation.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<User, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

}
