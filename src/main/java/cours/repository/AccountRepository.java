package cours.repository;

import cours.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByClientId(Long id);
    Account findByAccountType(String accountType);
}