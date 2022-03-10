package uz.narzullayev.javohir.validation.anontation;

import uz.narzullayev.javohir.validation.FieldMatchValidator;
import uz.narzullayev.javohir.validation.MultiPartValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
 @author: Javohir
  Date: 3/10/2022
  Time: 4:41 PM*/
@Target({FIELD, METHOD, TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MultiPartValidator.class)
public @interface NotEmptyMultipart {

    String message() default "MultipartFile not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
