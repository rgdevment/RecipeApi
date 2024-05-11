package cl.tica.portfolio.recipeapi.recipe.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryValidator.class)
public @interface ValidCountry {
    String message() default "The value provided for Country is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
