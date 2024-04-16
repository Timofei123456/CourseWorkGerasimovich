package cours.service;

import cours.entity.Account;

import java.util.List;

public interface AccountService extends Service<Account> {

    List<Account> readByClient(Long clientId);

    Account readByAccountType(String type);
}
