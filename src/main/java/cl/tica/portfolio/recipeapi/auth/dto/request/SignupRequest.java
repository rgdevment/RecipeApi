package cl.tica.portfolio.recipeapi.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Size(min = 4, max = 20) String username,
        @Size(max = 50) @Email String email,
        @Size(min = 6, max = 40) String password
) {}
