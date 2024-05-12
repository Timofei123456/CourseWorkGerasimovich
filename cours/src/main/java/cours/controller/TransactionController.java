package cours.controller;

import cours.entity.Transaction;
import cours.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/transaction")
public class TransactionController extends AbstractController<Transaction> {
    @Autowired
    private TransactionService service;

    @Override
    public TransactionService getService() {
        return service;
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@PathVariable long id) {
        List<Transaction> transactions = service.readByAccount(id);
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, headers, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByTransactionType(@PathVariable String type) {
        List<Transaction> transactions = service.readByTransactionType(type);
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, headers, HttpStatus.OK);
    }
}