package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.EmailType;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
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

