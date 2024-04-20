package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.UserRole;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceJpa implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceJpa(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    @Transactional()
    public User save(User user) {
        Optional<UserRole> optionalRole = roleRepository.findByName("ROLE_USER");

        if (optionalRole.isPresent()) {
            UserRole role = optionalRole.get();
            user.setRoles(Collections.singletonList(role));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional()
    public void deleteByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.ifPresent(userRepository::delete);
    }
}
