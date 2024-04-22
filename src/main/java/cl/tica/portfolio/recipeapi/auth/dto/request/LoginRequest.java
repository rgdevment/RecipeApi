package cl.tica.portfolio.recipeapi.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty String username,
        @NotEmpty String password) {
}
