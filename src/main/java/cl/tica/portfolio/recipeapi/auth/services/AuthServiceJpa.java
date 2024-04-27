package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public User save(User user) {
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");

        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            user.addRole(role);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authRepository.save(user);
    }
}
