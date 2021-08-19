package br.com.zupacademy.propostas.controllers.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Base64.getDecoder;

public class Base64Validator implements ConstraintValidator<Base64, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank())
            return false;

        try {
            getDecoder().decode(value);
            return true;
        } catch (IllegalArgumentException e) {
           return false;
        }
    }
}
