package cours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cours.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
