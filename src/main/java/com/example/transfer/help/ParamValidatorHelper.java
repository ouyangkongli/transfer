package com.example.transfer.help;

import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@UtilityClass
public class ParamValidatorHelper {
    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static void validate(Object object) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException("parameter null error");
        }
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations.size() > 0) {
            throw new IllegalArgumentException(constraintViolations.iterator().next().getMessage());
        }
    }
}
