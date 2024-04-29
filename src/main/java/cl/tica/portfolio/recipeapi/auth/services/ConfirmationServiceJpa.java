package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserVerificationToken;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.UserConfirmationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ConfirmationServiceJpa implements ConfirmationService {
    private final AuthRepository authRepository;
    private final UserConfirmationRepository userConfirmationRepository;

    public ConfirmationServiceJpa(AuthRepository authRepository, UserConfirmationRepository userConfirmationRepository) {
        this.authRepository = authRepository;
        this.userConfirmationRepository = userConfirmationRepository;
    }

    @Override
    @Transactional()
    public boolean confirmEmail(String code) {
        AtomicBoolean isConfirmed = new AtomicBoolean(false);

        userConfirmationRepository.findUserConfirmationByCode(code)
                .ifPresent(token ->
                        authRepository.findByUsernameIgnoreCase(token.getUser().getUsername())
                                .ifPresent(user -> {
                                    activateUser(user);
                                    userConfirmationRepository.delete(token);
                                    isConfirmed.set(true);
                                }));

        return isConfirmed.get();
    }

    @Override
    @Transactional()
    public void generateVerificationToken(User user) {
        UserVerificationToken userVerificationToken = new UserVerificationToken(user);
        userConfirmationRepository.save(userVerificationToken);
    }

    private void activateUser(User user) {
        user.setAccountEnabled(true);
        user.setEmailVerified(true);
        saveUser(user);
    }

    private void saveUser(User user) {
        authRepository.save(user);
    }
}
