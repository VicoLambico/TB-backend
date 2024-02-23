package ch.api.onlyquest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "heroes_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name", nullable = false)

    private String categoryName;
    @Column(name = "hero_LP", nullable = false)
    private int lp;
    @Column(name = "hero_dps", nullable = false)
    private int dps;
    @Column(name = "hero_energy", nullable = false)
    private int energy;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @OneToMany(mappedBy = "heroCategory")
//    @JsonIgnore
    private List<Hero> heroes;

    @OneToMany(mappedBy = "competenceCategory")
    @JsonBackReference(value = "competencesInCategory")
    private List<Competence> competences;

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public int getDps() {
        return dps;
    }

    public void setDps(int dps) {
        this.dps = dps;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonBackReference(value = "HeroesInCategory")
    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }
}
