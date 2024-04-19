package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.PhoneType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_phones")
public class UserPhone extends UserAudit {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty
    @Pattern(regexp = "\\d{10,}")
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private boolean isPrimary;

    @NotNull
    @Column(nullable = false)
    private boolean isVerified = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneType type;
}

