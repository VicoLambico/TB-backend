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
@Table(name = "heroes")
public class Hero  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hero_name", nullable = false)
    private String heroName;
    @Column(name = "hero_level", nullable = false)
    private int lvl;
    @Column(name = "hero_experience", nullable = false)
    private int experience;

    @Column(name = "hero_LP", nullable = false)
    private int lp;
    @Column(name = "hero_dps", nullable = false)
    private int dps;
    @Column(name = "hero_energy", nullable = false)
    private int energy;
    private String categoryName;

    //foreign key
    @ManyToOne
    @JoinColumn(name = "hero_category_id")
    private Category heroCategory;
    //foreign key
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userHeroes;



    @ManyToMany()
    //définir les colonnes qui vont être creer dans la table MANY-MANY
    @JoinTable(joinColumns = @JoinColumn(name = "hero_id"),inverseJoinColumns = @JoinColumn(name = "quest_id"))
    //annotation pour éviter une boucle infini
    @JsonIgnoreProperties("heroesInQuest")
    private List<Quest> quests = new ArrayList<>();

    @ManyToMany()
    //définir les colonnes qui vont être creer dans la table MANY-MANY
    @JoinTable(joinColumns = @JoinColumn(name = "hero_id"),inverseJoinColumns = @JoinColumn(name = "competence_id"))
    //annotation pour éviter une boucle infini
    @JsonIgnoreProperties("heroes")
    private List<Competence> competences = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public int getLevel() {
        return lvl;
    }

    public void setLevel(int level) {
        this.lvl = level;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int LP) {
        this.lp = LP;
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

    @JsonBackReference(value = "HeroesInCategory")
    public Category getHeroCategory() {
        return heroCategory;
    }

    public void setHeroCategory(Category category) {
        this.heroCategory = category;
    }
    @JsonBackReference(value = "HeroesUser")
    public User getUserHeroes() {
        return userHeroes;
    }

    public void setUserHeroes(User heroUser) {
        this.userHeroes = heroUser;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }
}
