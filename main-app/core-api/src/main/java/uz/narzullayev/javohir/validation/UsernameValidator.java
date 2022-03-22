package uz.narzullayev.javohir.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.service.UserService;
import uz.narzullayev.javohir.validation.anontation.UserValidity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

import static uz.narzullayev.javohir.util.ReflectionUtils.extractFieldValue;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsernameValidator implements ConstraintValidator<UserValidity,Object> {
    private final UserService userService;

    private String idField;
    private String usernameField;
    private String message;

    @Override
    public void initialize(UserValidity constraintAnnotation) {
        this.idField=constraintAnnotation.idField();
        this.usernameField=constraintAnnotation.usernameField();
        this.message=constraintAnnotation.message();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final var idVal = extractFieldValue(value, idField,Long.class);
            final var usernameVal = extractFieldValue(value, usernameField,String.class);

            //update
            if (idVal!=null) {
                var user = userService.findById(idVal);
                var isChangedEmail = Optional.ofNullable(user)
                        .map(UserEntity::getUsername)
                        .map(email -> !Objects.equals(email, usernameVal))
                        .orElse(Boolean.TRUE);
                if (isChangedEmail){
                    valid = userService.isUserAlreadyPresent(usernameVal);
                }
            }else {
                //create
                valid = userService.isUserAlreadyPresent(usernameVal);
            }

        } catch ( Exception ignore ) {
            log.warn(ignore.getMessage());
        }
        if ( !valid ){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(usernameField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }


}
