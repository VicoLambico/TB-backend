package ch.api.onlyquest.controllers;

import ch.api.onlyquest.models.LoginRequest;
import ch.api.onlyquest.models.User;

import ch.api.onlyquest.repositiories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createUser(@RequestBody User user) {
        user.setAdmin(false);
        if (userRepository.existsByEmailOrLogin(user.getEmail(), user.getLogin())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Login or Email already used.");
        }
        if (!isValidEmail(user.getEmail())) {
            return new ResponseEntity<>("Invalid e-mail format.", HttpStatus.BAD_REQUEST);
        }

        User createdUser = userRepository.save(user);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        // Récupérez l'utilisateur en fonction du nom d'utilisateur
        Optional<User> userOptional = userRepository.findByLogin(loginRequest.getLogin());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(loginRequest.getPassword())) {
                // L'authentification réussie
                return ResponseEntity.ok(user);
            } else {
                // Échec de l'authentification
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            // L'utilisateur n'existe pas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok().header("Location", "/login").body("Logout successful");
//        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setLogin(updatedUser.getLogin());
                    user.setPassword(updatedUser.getPassword());
                    userRepository.save(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Long id, @RequestBody User userUpdates) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userUpdates.getEmail() != null) {
                        user.setEmail(userUpdates.getEmail());
                    }
                    if (userUpdates.getLogin() != null) {
                        user.setLogin(userUpdates.getLogin());
                    }
                    if (userUpdates.getPassword() != null) {
                        user.setPassword(userUpdates.getPassword());
                    }
                    //uniquement les admin peuvent chager isAdmin
                    if(userUpdates.isAdmin() == true){
                        user.setAdmin(userUpdates.isAdmin());
                    }
                    userRepository.save(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}