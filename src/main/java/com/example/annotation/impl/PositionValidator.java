package com.example.annotation.impl;

import com.example.annotation.Gender;
import com.example.annotation.Position;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.util.ValidatorConstant.CHIEF_OF_A_TEACHING_AND_RESEARCH_SECTION;
import static com.example.util.ValidatorConstant.DISCIPLINE_LEADER;

public class PositionValidator implements ConstraintValidator<Position, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }
        return CHIEF_OF_A_TEACHING_AND_RESEARCH_SECTION.equals(value)  || DISCIPLINE_LEADER.equals(value);
    }
}
