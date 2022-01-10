package uz.narzullayev.javohir.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.narzullayev.javohir.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UserValidity,String> {
    private final UserService userService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return !userService.isUserAlreadyPresent(s) && !userService.existByEmail(s);
    }
}
