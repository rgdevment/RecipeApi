package cl.tica.portfolio.recipeapi.auth.dto.response;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.UserAdditionalData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UpdatedUserResponse(
        String username,
        String email,
        List<Role> roles,
        @JsonProperty("additional_data") UserAdditionalData additionalData
) {
}
