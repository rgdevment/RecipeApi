package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users_additional_data")
public class UserAdditionalData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String lastname;

    @NotNull
    @Column(nullable = false)
    private GenderType gender;

    public UserAdditionalData() {
    }

    public UserAdditionalData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public @NotBlank String getName() {
        return name;
    }

    public @NotBlank String getLastname() {
        return lastname;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public void setLastname(@NotBlank String lastname) {
        this.lastname = lastname;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }
}
