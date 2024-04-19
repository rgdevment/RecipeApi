package cl.tica.portfolio.recipeapi.auth.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username")
})
public class User extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private boolean isEnabled = false; //activate with token confirmation

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInformation userInformation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserEmail> emails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPhone> phones;
}
