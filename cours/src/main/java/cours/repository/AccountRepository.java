package cours.repository;

import cours.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByClient(Long id);

    Account findByAccountType(String accountType);
}