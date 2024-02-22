package ch.api.onlyquest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "quest")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quest_name", nullable = false)
    private String questName;
    @Column(name = "quest_level", nullable = false)
    private int questLevel;
    @Column(name = "quest_LP", nullable = false)
    private int lp;
    @Column(name = "quest_dps", nullable = false)
    private int dps;
    @Column(name = "quest_description", nullable = false, length = 2000)
    private String description;

    @ManyToMany(mappedBy = "quests")
    //annotation pour éviter une boucle infini
    @JsonIgnoreProperties("quests")
    private List<Hero> heroesInQuest = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public int getQuestLevel() {
        return questLevel;
    }

    public void setQuestLevel(int questLevel) {
        this.questLevel = questLevel;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Hero> getHeroesInQuest() {
        return heroesInQuest;
    }

    public void setHeroesInQuest(List<Hero> heroesInQuest) {
        this.heroesInQuest = heroesInQuest;
    }
}
