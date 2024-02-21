package ch.api.onlyquest.controllers;

import ch.api.onlyquest.models.Category;
import ch.api.onlyquest.repositiories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllHeroCategories() {
        List<Category> heroCategories = categoryRepository.findAll();
        return new ResponseEntity<>(heroCategories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getHeroCategoryById(@PathVariable Long id) {
        Optional<Category> optionalHeroCategory = categoryRepository.findById(id);
        return optionalHeroCategory.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createHeroCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateHeroCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setCategoryName(updatedCategory.getCategoryName());
                    category.setDescription(updatedCategory.getDescription());
                    categoryRepository.save(category);
                    return new ResponseEntity<>(category, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteHeroCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<Category> partialUpdateCategory(@PathVariable Long id, @RequestBody Category categoryUpdates) {
        return categoryRepository.findById(id)
                .map(category -> {
                    if (categoryUpdates.getCategoryName() != null && categoryUpdates.getCategoryName().trim() != "") {
                        category.setCategoryName(categoryUpdates.getCategoryName());
                    }
                    if (categoryUpdates.getDescription() != null && categoryUpdates.getDescription().trim() != "") {
                        category.setDescription(categoryUpdates.getDescription());
                    }
                    // Ajoutez d'autres conditions pour les propriétés que vous souhaitez mettre à jour
                    categoryRepository.save(category);
                    return new ResponseEntity<>(category, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}