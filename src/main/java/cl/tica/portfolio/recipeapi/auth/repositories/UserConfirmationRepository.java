package cl.tica.portfolio.recipeapi.auth.repositories;

import cl.tica.portfolio.recipeapi.auth.entities.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
public interface UserConfirmationRepository extends JpaRepository<UserVerificationToken, Long> {
    Optional<UserVerificationToken> findUserConfirmationByCode(String code);
}
