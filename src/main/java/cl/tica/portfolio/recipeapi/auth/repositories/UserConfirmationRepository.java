package cl.tica.portfolio.recipeapi.auth.repositories;

import cl.tica.portfolio.recipeapi.auth.entities.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserConfirmationRepository extends JpaRepository<UserVerificationToken, Long> {
    UserVerificationToken findUserConfirmationByCode(String code);
}
