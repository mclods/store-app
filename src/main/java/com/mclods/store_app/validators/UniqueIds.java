package com.mclods.store_app.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FullUpdateUserAddressUniqueIdsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIds {
    String message() default "Ids must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
