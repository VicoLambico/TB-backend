package ch.api.onlyquest.controllers;

import ch.api.onlyquest.models.Competence;
import ch.api.onlyquest.repositiories.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompetenceController {

    @Autowired
    private CompetenceRepository competenceRepository;

    @GetMapping("/competence")
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    @GetMapping("/competence/{id}")
    public ResponseEntity<Competence> getCompetenceById(@PathVariable Long id) {
        return competenceRepository.findById(id)
                .map(competence -> new ResponseEntity<>(competence, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/competence", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Competence> createCompetence(@RequestBody Competence competence) {
        Competence savedCompetence = competenceRepository.save(competence);
        return new ResponseEntity<>(savedCompetence, HttpStatus.CREATED);
    }

    @PatchMapping("/competence/{id}")
    public ResponseEntity<Competence> partialUpdateCompetence(@PathVariable Long id, @RequestBody Competence competenceUpdates) {
        return competenceRepository.findById(id)
                .map(competence -> {
                    if (competenceUpdates.getName() != null && competenceUpdates.getName().trim() != "") {
                        competence.setName(competenceUpdates.getName());
                    }
                    if (competenceUpdates.getDescription() != null && competenceUpdates.getDescription().trim() != "") {
                        competence.setDescription(competenceUpdates.getDescription());
                    }
                    if (competenceUpdates.getDamage() != 0) {
                        competence.setDamage(competenceUpdates.getDamage());
                    }
                    // Ajoutez d'autres conditions pour les propriétés que vous souhaitez mettre à jour

                    if (competenceUpdates.getCompetenceCategory() != null) {
                        competence.setCompetenceCategory(competenceUpdates.getCompetenceCategory());
                    }
                    competenceRepository.save(competence);
                    return new ResponseEntity<>(competence, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/competence/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        competenceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}