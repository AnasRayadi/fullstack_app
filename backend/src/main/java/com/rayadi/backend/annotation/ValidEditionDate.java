package com.rayadi.backend.annotation;

import com.rayadi.backend.constants.ExceptionMessagesConstant;
import com.rayadi.backend.validator.EditionDateValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Constraint(validatedBy = EditionDateValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEditionDate {
    String message() default ExceptionMessagesConstant.INVALID_EDITION_DATE;
    Class<?>[] groups() default {};
    Class<? extends Annotation>[] payload() default {};
}
