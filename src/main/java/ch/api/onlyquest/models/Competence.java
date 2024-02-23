package ch.api.onlyquest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "competences")
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false,  length = 2000)
    private String description;
    @Column(name = "damage", nullable = false)
    private int damage;


    @ManyToOne
    @JoinColumn(name = "competence_category_id")
    private Category competenceCategory;


    @ManyToMany(mappedBy = "competences")
    //annotation pour éviter une boucle infini
    @JsonIgnoreProperties("competences")
    private List<Hero> heroes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int bonus) {
        this.damage = bonus;
    }

    @JsonBackReference(value = "competencesInCategory")
    public Category getCompetenceCategory() {
        return competenceCategory;
    }

    public void setCompetenceCategory(Category heroCompetence) {
        this.competenceCategory = heroCompetence;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }
}
