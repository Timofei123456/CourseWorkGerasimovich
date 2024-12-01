package cours.controller;

import cours.entity.*;
import cours.service.TransactionService;
import cours.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService service;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> entities = service.read();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long transactionId) {
        Transaction transaction = service.read(transactionId);
        return checkEntityAndRole(transaction);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@PathVariable long id) {
        List<Transaction> transactions = service.readByAccount(id);
        return checkListOfEntityAndRole(transactions);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByTransactionType(@PathVariable String type) {
        List<Transaction> transactions = service.readByTransactionType(type);
        return checkListOfEntityAndRole(transactions);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<List<Transaction>> getTransactionsOfCurrentUser () {
        User currentUser  = getCurrentUser();
        if (currentUser  == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Account> userAccounts = currentUser.getClient().getAccounts();
        List<Transaction> userTransactions = new ArrayList<>();

        for (Account account : userAccounts) {
            List<Transaction> transactions = service.readByAccount(account.getId());
            userTransactions.addAll(transactions);
        }

        return checkListOfEntityAndRole(userTransactions);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putTransaction(@RequestBody Transaction entity) {
        service.edit(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postTransaction(@RequestBody Transaction entity) {
        User currentUser = getCurrentUser();

        List<Account> userAccounts = currentUser.getClient().getAccounts();

        if (userAccounts.stream().anyMatch(account -> account.getId().equals(entity.getAccount().getId()))) {
            service.save(entity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN) || currentUser.getRole().equals(Role.ROLE_SUPERADMIN)) {
            service.save(entity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/sortedByDate")
    public ResponseEntity<List<Transaction>> getAllTransactionsSortedByDate() {
        List<Transaction> entities = service.readAllSortedByDate();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransactionById(@PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteAllTransactions() {
        service.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.getByUsername(currentPrincipalName);
    }

    private ResponseEntity<Transaction> checkEntityAndRole(Transaction transaction) {
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User currentUser = getCurrentUser();

        Client userClient = currentUser.getClient();
        if (userClient != null) {
            if (currentUser.getRole().equals(Role.ROLE_USER) &&
                    userClient.getAccounts().stream().anyMatch(account -> account.getId().equals(transaction.getAccount().getId()))
                    ) {
                return new ResponseEntity<>(transaction, HttpStatus.OK);
            }
        }
        if (currentUser .getRole().equals(Role.ROLE_ADMIN) || currentUser .getRole().equals(Role.ROLE_SUPERADMIN)) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<List<Transaction>> checkListOfEntityAndRole(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Transaction> allowedTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            ResponseEntity<Transaction> responseEntity = checkEntityAndRole(transaction);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                allowedTransactions.add(transaction);
            }
        }

        if (allowedTransactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(allowedTransactions, HttpStatus.OK);
    }
}