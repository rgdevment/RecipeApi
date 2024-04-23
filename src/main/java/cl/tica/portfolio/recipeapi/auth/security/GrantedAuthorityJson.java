package cl.tica.portfolio.recipeapi.auth.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GrantedAuthorityJson {
    @JsonCreator
    GrantedAuthorityJson(@JsonProperty("authority") String role) {
    }
}
