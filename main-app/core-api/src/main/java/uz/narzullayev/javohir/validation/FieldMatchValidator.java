package uz.narzullayev.javohir.validation;

import org.springframework.stereotype.Component;
import uz.narzullayev.javohir.validation.anontation.FieldMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static uz.narzullayev.javohir.util.ReflectionUtils.extractFieldValue;

@Component
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();

        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        var valid = true;
        try {
            final String firstObj = extractFieldValue(value, firstFieldName,String.class);
            final String secondObj = extractFieldValue(value, secondFieldName,String.class);
            valid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);

        } catch (final Exception ignore) {
            ignore.printStackTrace();
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }




}
