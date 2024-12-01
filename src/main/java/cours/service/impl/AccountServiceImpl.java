package cours.service.impl;

import cours.entity.Account;
import cours.entity.Client;
import cours.repository.AccountRepository;
import cours.repository.ClientRepository;
import cours.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Account read(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public List<Account> read() {
        return accountRepository.findAll();
    }
    @Override
    public void save(Account entity) {
        Client client = entity.getClient();
        Long id = client.getId();
        client = clientRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        entity.setClient(client);
        accountRepository.save(entity);
        client.getAccounts().add(entity);
        clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.findById(id).get();
        Client client = account.getClient();
        client.getAccounts().remove(account);
        clientRepository.save(client);
    }

    @Override
    public Account readByAccountType(String type) {
        return accountRepository.findByAccountType(type);
    }

    @Override
    public List<Account> readByClient(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }

    @Override
    public void edit(Account entity) {
        Client client = entity.getClient();
        Long cId = client.getId();
        Long aId = entity.getId();
        client = clientRepository.findById(cId).orElseThrow(IllegalArgumentException::new);
        Account account = client.getAccounts().stream().filter(a -> aId.equals(a.getId())).findAny()
                .orElseThrow(IllegalArgumentException::new);
        account.setAccountType(entity.getAccountType());
        account.setAccountNumber(entity.getAccountNumber());
        account.setBalance(entity.getBalance());
        accountRepository.save(account);
    }
}