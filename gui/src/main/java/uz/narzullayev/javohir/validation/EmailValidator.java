package uz.narzullayev.javohir.validation;/* 
 @author: Javohir
  Date: 1/16/2022
  Time: 3:11 PM*/


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.narzullayev.javohir.entity.UserEntity;
import uz.narzullayev.javohir.service.UserService;
import uz.narzullayev.javohir.validation.anontation.EmailValidity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.Optional;

import static uz.narzullayev.javohir.util.ReflectionUtils.extractFieldValue;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailValidator  implements ConstraintValidator<EmailValidity,Object> {
    private final UserService userService;

    private String idField;
    private String emailField;
    private String message;


    @Override
    public void initialize(EmailValidity constraintAnnotation) {
        this.idField=constraintAnnotation.idField();
        this.emailField=constraintAnnotation.emailField();
        this.message=constraintAnnotation.message();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Long idVal = extractFieldValue(value, idField,Long.class);
            final String emailValue = extractFieldValue(value, emailField,String.class);

            //update
            if (idVal!=null) {
                UserEntity user = userService.findById(idVal);
                Boolean isChangedEmail = Optional.ofNullable(user)
                        .map(UserEntity::getEmail)
                        .map(email -> !Objects.equals(email, emailValue))
                        .orElse(Boolean.TRUE);
                if (isChangedEmail){
                    valid = !userService.existByEmail(emailValue);
                }
            }else {
             //create
                valid = !userService.existByEmail(emailValue);
            }

        } catch ( Exception ignore ) {
            log.warn(ignore.getMessage());
        }
        if ( !valid ){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(emailField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
