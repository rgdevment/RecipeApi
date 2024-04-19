package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.AddressType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_addresses")
public class UserAddress extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty
    @Column(nullable = false)
    private String street;

    @NotEmpty
    @Column(nullable = false)
    private String city;

    @NotEmpty
    @Column(nullable = false)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AddressType type;
}

