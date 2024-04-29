package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;

public interface AuthenticationService {
    User register(User user);
}
