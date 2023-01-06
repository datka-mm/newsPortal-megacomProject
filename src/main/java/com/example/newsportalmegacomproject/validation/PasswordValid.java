package com.example.newsportalmegacomproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {

    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
