package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;

public interface UserAdditionalDataService {
    User findUserByUsername(String username);
    User updateUserData(User user);
}
