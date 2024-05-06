package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "units_measures")
public class UnitMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "units_measures_seq")
    @SequenceGenerator(name = "units_measures_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(unique = true, nullable = false)
    private String name;
}
