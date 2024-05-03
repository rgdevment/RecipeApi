package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.GenderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users_additional_data")
public class UserAdditionalData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
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

    public @NotEmpty String getName() {
        return name;
    }

    public @NotEmpty String getLastname() {
        return lastname;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public void setLastname(@NotEmpty String lastname) {
        this.lastname = lastname;
    }

    public void setName(@NotEmpty String name) {
        this.name = name;
    }
}