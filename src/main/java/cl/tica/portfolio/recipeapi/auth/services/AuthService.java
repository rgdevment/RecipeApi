package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;

public interface AuthService {
    User register(User user);

    boolean confirmEmail(String confirmationToken);
}
