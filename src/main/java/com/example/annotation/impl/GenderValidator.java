package com.example.annotation.impl;

import com.example.annotation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.util.ValidatorConstant.FEMALE;
import static com.example.util.ValidatorConstant.MALE;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }
        return MALE.equals(value)  || FEMALE.equals(value);
    }
}
