package ch.api.onlyquest.controllers;

import ch.api.onlyquest.models.Category;
import ch.api.onlyquest.models.Competence;
import ch.api.onlyquest.models.Hero;
import ch.api.onlyquest.models.User;
import ch.api.onlyquest.repositiories.HeroRepository;
import ch.api.onlyquest.repositiories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class HeroController {

    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/heroes")
    public List<Hero> getAllHeroes() {
        return heroRepository.findAll();
    }

    @GetMapping("/heroes/{id}")
    public ResponseEntity<Hero> getHeroById(@PathVariable Long id) {
        return heroRepository.findById(id)
                .map(hero -> new ResponseEntity<>(hero, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/heroes", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Hero> createHero(@RequestBody Hero hero, Long userId) {
        if (hero.getHeroCategory() == null) {
            Category category = new Category();
            category.setCategoryName("null");
            category.setDps(0);
            category.setLp(0);
            category.setEnergy(0);
            hero.setHeroCategory(category);
            hero.setDps(category.getDps());
            hero.setLp(category.getLp());
            hero.setEnergy(category.getEnergy());

        } else {
            Category heroCategory = hero.getHeroCategory();
            hero.setHeroCategory(heroCategory);
            hero.setDps(heroCategory.getDps());
            hero.setLp(heroCategory.getLp());
            hero.setEnergy(heroCategory.getEnergy());
            hero.setCategoryName(heroCategory.getCategoryName());
            hero.setExperience(0);

            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                hero.setUserHeroes(user);
            }else {
                hero.setUserHeroes(null);
            }

        }
        hero.setLvl(1);
        Hero savedHero = heroRepository.save(hero);
        return new ResponseEntity<>(savedHero, HttpStatus.CREATED);
    }

    @PutMapping("/heroes/{id}")
    public ResponseEntity<Hero> updateHero(@PathVariable Long id, @RequestBody Hero updatedHero) {
        return heroRepository.findById(id)
                .map(hero -> {
                    hero.setHeroName(updatedHero.getHeroName());
                    hero.setLevel(updatedHero.getLevel());
                    hero.setLp(updatedHero.getLp());
                    hero.setDps(updatedHero.getDps());
                    hero.setEnergy(updatedHero.getEnergy());
                    hero.setHeroCategory(updatedHero.getHeroCategory());
                    hero.setUserHeroes(updatedHero.getUserHeroes());
                    hero.setQuests(updatedHero.getQuests());
                    hero.setCompetences(updatedHero.getCompetences());

                    heroRepository.save(hero);
                    return new ResponseEntity<>(hero, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/heroes/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable Long id) {
        return heroRepository.findById(id)
                .map(hero -> {
                    heroRepository.delete(hero);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/heroes/{id}")
    public ResponseEntity<Hero> partialUpdateHero(@PathVariable Long id, @RequestBody Hero heroUpdates) {
        return heroRepository.findById(id)
                .map(hero -> {
                    if (heroUpdates.getHeroName() != null && heroUpdates.getHeroName().trim() != "") {
                        hero.setHeroName(heroUpdates.getHeroName());
                    }
                    if (heroUpdates.getLevel() != 0) {
                        hero.setLevel(heroUpdates.getLevel());
                    }
                    if (heroUpdates.getExperience() != 0) {
                        hero.setExperience(heroUpdates.getExperience());
                    }
                    if (heroUpdates.getLp() != 0) {
                        hero.setLp(heroUpdates.getLp());
                    }
                    if (heroUpdates.getDps() != 0) {
                        hero.setDps(heroUpdates.getDps());
                    }
                    if (heroUpdates.getEnergy() != 0) {
                        hero.setEnergy(heroUpdates.getEnergy());
                    }
                    if (heroUpdates.getHeroCategory() != null) {
                        hero.setHeroCategory(heroUpdates.getHeroCategory());
                    }
                    if (heroUpdates.getUserHeroes() != null) {
                        hero.setUserHeroes(heroUpdates.getUserHeroes());
                    }
                    if (heroUpdates.getQuests() != null) {
                        hero.setQuests(heroUpdates.getQuests());
                    }
                    if (heroUpdates.getCompetences() != null) {
                        hero.setCompetences(heroUpdates.getCompetences());
                    }

                    heroRepository.save(hero);
                    return new ResponseEntity<>(hero, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}