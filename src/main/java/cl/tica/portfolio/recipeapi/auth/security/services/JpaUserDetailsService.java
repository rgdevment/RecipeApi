package cl.tica.portfolio.recipeapi.auth.security.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final AuthRepository repository;

    public JpaUserDetailsService(AuthRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s not found", username)));

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(this::createGrantedAuthority).toList();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isAccountEnabled(),
                true,
                true,
                !user.isAccountLocked(),
                grantedAuthorities
        );
    }

    private GrantedAuthority createGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getName());
    }
}
