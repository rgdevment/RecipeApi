package cl.tica.portfolio.recipeapi.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotEmpty @Size(min = 4, max = 20) String username,
        @NotEmpty @Size(max = 50) @Email String email,
        @NotEmpty @Size(min = 6, max = 40) String password
) {
}
