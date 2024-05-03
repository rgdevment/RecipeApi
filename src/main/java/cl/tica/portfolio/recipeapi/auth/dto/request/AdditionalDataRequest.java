package cl.tica.portfolio.recipeapi.auth.dto.request;

import cl.tica.portfolio.recipeapi.auth.validations.ValidGender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdditionalDataRequest(
        @NotEmpty @Size(min = 4, max = 20) String name,
        @NotEmpty @Size(min = 4, max = 20) String lastname,
        @NotNull @ValidGender String gender
) {
}
