package uz.narzullayev.javohir.validation.anontation;

import uz.narzullayev.javohir.validation.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD , TYPE})
@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Retention(RUNTIME)
public @interface UserValidity {
    String message() default "There is already user with this username!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    String idField() default "id";
    String usernameField() default "username";
}
