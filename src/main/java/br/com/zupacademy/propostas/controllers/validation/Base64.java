package br.com.zupacademy.propostas.controllers.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { Base64Validator.class} )
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64 {
    String message() default "{validation.base64.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
