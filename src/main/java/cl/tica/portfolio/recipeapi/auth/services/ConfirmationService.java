package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;

public interface ConfirmationService {
    boolean confirmEmail(String confirmationToken);

    void generateVerificationToken(User user);
}
