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
    private AccountRepository aRepository;
    @Autowired
    private ClientRepository cRepository;

    @Override
    public Account read(Long id) {
        return aRepository.findById(id).get();
    }

    @Override
    public List<Account> read() {
        return aRepository.findAll();
    }

    @Override
    public void save(Account entity) {
        Client client = entity.getClient();
        Long id = client.getId();
        client = cRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        entity.setClient(client);
        client.getAccounts().add(entity);
        cRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        Account account = aRepository.findById(id).get();
        Client client = account.getClient();
        client.getAccounts().remove(account);
        cRepository.save(client);
    }

    @Override
    public Account readByAccountType(String type) {
        return aRepository.findByAccountType(type);
    }

    @Override
    public List<Account> readByClient(Long clientId) {
        return aRepository.findByClientId(clientId);
    }

    @Override
    public void edit(Account entity) {
        Client client = entity.getClient();
        Long fId = client.getId();
        Long gId = entity.getId();
        client = cRepository.findById(fId).orElseThrow(IllegalArgumentException::new);
        Account account = client.getAccounts().stream().filter(g -> gId.equals(g.getId())).findAny()
                .orElseThrow(IllegalArgumentException::new);
        account.setClient(client);
        account.setAccountType(entity.getAccountType());
        client.getAccounts().add(account);
        aRepository.save(account);
    }
}