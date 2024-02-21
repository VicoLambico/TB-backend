package ch.api.onlyquest.repositiories;



import ch.api.onlyquest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByLogin(String login);


    boolean existsByEmailOrLogin(String email, String login);
}
