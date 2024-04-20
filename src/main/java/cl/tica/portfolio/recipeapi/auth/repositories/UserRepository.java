package cl.tica.portfolio.recipeapi.auth.repositories;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String username);
}
