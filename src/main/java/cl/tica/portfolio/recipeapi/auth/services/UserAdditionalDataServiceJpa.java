package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAdditionalDataServiceJpa implements UserAdditionalDataService {
    private final AuthRepository authRepository;

    public UserAdditionalDataServiceJpa(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return authRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    @Transactional()
    public void updateUserData(User user) {
        authRepository.save(user);
    }
}
