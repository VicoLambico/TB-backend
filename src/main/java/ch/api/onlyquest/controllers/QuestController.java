package ch.api.onlyquest.controllers;

import ch.api.onlyquest.models.Quest;
import ch.api.onlyquest.repositiories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestController {

    private final QuestRepository questRepository;

    @Autowired
    public QuestController(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    // Endpoint pour récupérer toutes les quêtes
    @GetMapping("/quest")
    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }

    // Endpoint pour récupérer une quête par son ID
    @GetMapping("/quest/{id}")
    public ResponseEntity<Quest> getQuestById(@PathVariable Long id) {
        return questRepository.findById(id)
                .map(quest -> new ResponseEntity<>(quest, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour créer une nouvelle quête
    @PostMapping(value = "/quest", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Quest> createQuest(@RequestBody Quest quest) {
        Quest savedQuest = questRepository.save(quest);
        return new ResponseEntity<>(savedQuest, HttpStatus.CREATED);
    }

    // Endpoint pour mettre à jour une quête par son ID
    @PatchMapping("/quest/{id}")
    public ResponseEntity<Quest> partialUpdateQuest(@PathVariable Long id, @RequestBody Quest questUpdates) {
        return questRepository.findById(id)
                .map(quest -> {
                    if (questUpdates.getQuestName() != null && questUpdates.getQuestName().trim() != "") {
                        quest.setQuestName(questUpdates.getQuestName());
                    }
                    if (questUpdates.getQuestLevel() != 0) {
                        quest.setQuestLevel(questUpdates.getQuestLevel());
                    }
                    if (questUpdates.getLp() != 0) {
                        quest.setLp(questUpdates.getLp());
                    }
                    if (questUpdates.getDps() != 0) {
                        quest.setDps(questUpdates.getDps());
                    }
                    if (questUpdates.getExperience() != 0) {
                        quest.setExperience(questUpdates.getExperience());
                    }
                    if (questUpdates.getDescription() != null&& questUpdates.getDescription().trim() != "") {
                        quest.setDescription(questUpdates.getDescription());
                    }

                    // Ajoutez d'autres conditions pour mettre à jour d'autres propriétés
                    questRepository.save(quest);
                    return new ResponseEntity<>(quest, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour supprimer une quête par son ID
    @DeleteMapping("/quest/{id}")
    public ResponseEntity<Void> deleteQuest(@PathVariable Long id) {
        questRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}