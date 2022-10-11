package com.internationalmessenger.api.validator;

import com.internationalmessenger.api.validator.impl.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email is already taken";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
