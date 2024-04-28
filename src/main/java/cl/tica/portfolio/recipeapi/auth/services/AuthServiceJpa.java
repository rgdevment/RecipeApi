package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static cl.tica.portfolio.recipeapi.auth.entities.Role.DEFAULT_ROLE;

@Service
public class AuthServiceJpa implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthServiceJpa(AuthRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return this.authRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return authRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return authRepository.existsByEmail(email);
    }

    @Override
    @Transactional()
    public User register(User user) {
        validateNewUser(user);
        assignDefaultRole(user);
        encryptPassword(user);
        return saveUser(user);
    }

    private void validateNewUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException(HttpStatus.CONFLICT, "Username is already taken.");
        }
        if (existsByEmail(user.getEmail())) {
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

    private User saveUser(User user) {
        return authRepository.save(user);
    }
}
