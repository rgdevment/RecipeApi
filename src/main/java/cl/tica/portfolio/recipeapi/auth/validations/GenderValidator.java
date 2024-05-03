package cl.tica.portfolio.recipeapi.auth.validations;

import cl.tica.portfolio.recipeapi.auth.enums.GenderType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    private String genderTypes;

    private static final String INVALID_GENDER_TYPE_MESSAGE =
            "The value provided for GenderType is invalid. Valid values are: [%s]";

    @Override
    public void initialize(ValidGender constraintAnnotation) {
        // Get all gender types at initialization time
        genderTypes = Arrays.stream(GenderType.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        boolean isValid = Arrays.stream(GenderType.values())
                .anyMatch(genderType -> genderType.name().equalsIgnoreCase(value));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format(INVALID_GENDER_TYPE_MESSAGE, genderTypes))
                    .addConstraintViolation();
        }

        return isValid;
    }
}
