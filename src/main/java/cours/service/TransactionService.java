package cours.service;

import cours.entity.Transaction;

import java.util.List;

public interface TransactionService extends Service<Transaction> {

    List<Transaction> readByAccount(Long accountId);

    List<Transaction> readByTransactionType(String type);

    void deleteAll();

    List<Transaction> readAllSortedByDate();
}
