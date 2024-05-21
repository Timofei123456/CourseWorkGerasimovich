package cours.service.impl;

import cours.entity.Account;
import cours.entity.Transaction;
import cours.repository.AccountRepository;
import cours.repository.TransactionRepository;
import cours.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Transaction read(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public List<Transaction> read() {
        return transactionRepository.findAll();
    }

    @Override
    public void save(Transaction entity) {
        Account account = entity.getAccount();
        Long id = account.getId();
        account = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        entity.setAccount(account);
        account.getTransactions().add(entity);
        accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id).get();
        Account account = transaction.getAccount();
        account.getTransactions().remove(transaction);
        accountRepository.save(account);
    }

    @Override
    public List<Transaction> readByTransactionType(String type) {
        return transactionRepository.findByTransactionType(type);
    }

    @Override
    public List<Transaction> readByAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Override
    public void edit(Transaction entity) {
        Account account = entity.getAccount();
        Long gId = account.getId();
        Long sId = entity.getId();
        account = accountRepository.findById(gId).orElseThrow(IllegalArgumentException::new);
        Transaction transaction = account.getTransactions().stream().filter(s -> sId.equals(s.getId())).findAny()
                .orElseThrow(IllegalArgumentException::new);
        transaction.setAccount(account);
        transaction.setTransactionType(entity.getTransactionType());
        transaction.setTransactionDate(entity.getTransactionDate());
        transaction.setAmount(entity.getAmount());
        account.getTransactions().add(transaction);
        transactionRepository.save(transaction);
    }
}