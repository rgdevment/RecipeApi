package cl.tica.portfolio.recipeapi.auth.dto.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
