package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserVerificationToken;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.UserConfirmationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static cl.tica.portfolio.recipeapi.auth.entities.Role.DEFAULT_ROLE;

@Service
public class AuthServiceJpa implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserConfirmationRepository userConfirmationRepository;

    public AuthServiceJpa(AuthRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                          UserConfirmationRepository userConfirmationRepository) {
        this.authRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userConfirmationRepository = userConfirmationRepository;
    }

    @Override
    @Transactional()
    public User register(User user) {
        validateNewUser(user);
        assignDefaultRole(user);
        encryptPassword(user);
        User savedUser = saveUser(user);
        generateVerificationCode(savedUser);

        return savedUser;
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

    private void validateNewUser(User user) {
        if (authRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException(HttpStatus.CONFLICT, "Username is already taken.");
        }
        if (authRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException(HttpStatus.CONFLICT, "The email is already registered.");
        }
    }

    private void assignDefaultRole(User user) {
        Optional<Role> optionalRole = roleRepository.findByName(DEFAULT_ROLE);
        optionalRole.ifPresent(user::addRole);
    }

    private void encryptPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void generateVerificationCode(User savedUser) {
        UserVerificationToken userVerificationToken = new UserVerificationToken(savedUser);
        userConfirmationRepository.save(userVerificationToken);
    }

    private void activateUser(User user) {
        user.setAccountEnabled(true);
        user.setEmailVerified(true);
        saveUser(user);
    }

    private User saveUser(User user) {
        return authRepository.save(user);
    }
}
