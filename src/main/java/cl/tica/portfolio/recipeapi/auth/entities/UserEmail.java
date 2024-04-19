package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.EmailType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_emails", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class UserEmail extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean isPrimary;

    @NotNull
    @Column(nullable = false)
    private boolean isVerified = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType type;
}

