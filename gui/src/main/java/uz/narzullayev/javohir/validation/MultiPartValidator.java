package uz.narzullayev.javohir.validation;/* 
 @author: Javohir
  Date: 3/10/2022
  Time: 4:42 PM*/

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.WithBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.narzullayev.javohir.validation.anontation.NotEmptyMultipart;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiPartValidator implements ConstraintValidator<NotEmptyMultipart, MultipartFile> {
    String message;

    @Override
    public void initialize(NotEmptyMultipart constraintAnnotation) {
        message = constraintAnnotation.message();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        var valid = true;
        try {
            valid = value != null && !value.isEmpty();
        } catch (final Exception ignore) {
            ignore.printStackTrace();
            // ignore
            message = ignore.getMessage();
            valid = false;
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
