package cl.tica.portfolio.recipeapi.auth.dto.response;

import cl.tica.portfolio.recipeapi.auth.entities.Role;

import java.util.List;

public record RegisteredUserResponse(
        String username,
        String email,
        List<Role> roles
) {
}
