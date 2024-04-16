package cours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cours.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByName(String clientName);

}


