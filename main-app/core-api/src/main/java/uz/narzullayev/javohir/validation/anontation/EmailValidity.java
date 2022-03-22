package uz.narzullayev.javohir.validation.anontation;

import uz.narzullayev.javohir.validation.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/*
 @author: Javohir
  Date: 1/16/2022
  Time: 3:10 PM*/
@Target({ FIELD, METHOD , TYPE })
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Retention(RUNTIME)
public @interface EmailValidity {
    String message() default "There is already user with this email!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    String idField() default "id";
    String emailField() default "email";
}

