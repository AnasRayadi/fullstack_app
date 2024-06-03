package com.rayadi.backend.validator;

import com.rayadi.backend.annotation.ValidEditionDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class EditionDateValidator implements ConstraintValidator<ValidEditionDate, LocalDate> {
    @Override
    public void initialize(ValidEditionDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate editionDate, ConstraintValidatorContext context) {
        if (editionDate == null) {
            return true; // Allow null values, use @NotNull to enforce non-null constraint
        }
        return !editionDate.isAfter(LocalDate.now());
    }
}
