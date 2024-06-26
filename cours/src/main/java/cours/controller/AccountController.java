package cours.controller;

import cours.entity.Account;
import cours.entity.Role;
import cours.entity.User;
import cours.service.AccountService;
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
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService service;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> entities = service.read();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable long accountId) {
        Account account = service.read(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double balance = account.getTransactions().stream()
                .mapToDouble(transaction -> {
                    if (transaction.getTransactionType().equalsIgnoreCase("withdrawal")) {
                        return (-transaction.getAmount());
                    } else if (transaction.getTransactionType().equalsIgnoreCase("DEPOSIT")) {
                        return transaction.getAmount();
                    } else {
                        return 0;
                    }
                })
                .sum();
        balance = Math.round(balance * 100.0) / 100.0;
        double currentBalance = account.getBalance() + balance;
        currentBalance = Math.round(currentBalance * 100.0) / 100.0;
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (currentUser.getClient().getAccounts().stream()
                .anyMatch(acc -> acc.getId().equals(account.getId())) &&
                currentUser.getRole().equals(Role.ROLE_USER)) {
            return new ResponseEntity<>(currentBalance, HttpStatus.OK);
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN) || currentUser.getRole().equals(Role.ROLE_SUPERADMIN)) {
            return new ResponseEntity<>(currentBalance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable long accountId) {
        Account account = service.read(accountId);
        return checkEntityAndRole(account);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Account> getAccountByType(@PathVariable String type) {
        Account account = service.readByAccountType(type);
        return checkEntityAndRole(account);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Account>> getAccountsByClient(@PathVariable long id) {
        List<Account> accounts = service.readByClient(id);
        return checkListOfEntityAndRole(accounts);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putAccount(@RequestBody Account entity) {
        service.edit(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/{idAccountForCopy}/{idAccountForEdit}")
    public ResponseEntity<String> putAccount(@PathVariable long idAccountForCopy, @PathVariable long idAccountForEdit) {
    Account accountForCopy = service.read(idAccountForCopy);
    Account accountForEdit = service.read(idAccountForEdit);

    if (accountForCopy == null || accountForEdit == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
        accountForEdit.setAccountType(accountForCopy.getAccountType());
        accountForEdit.setTransactions(new ArrayList<>(accountForCopy.getTransactions());
        accountForEdit.setAccountNumber(accountForCopy.getAccountNumber());
        accountForEdit.setBalance(accountForCopy.getBalance());
        accountForEdit.getClient().setId(accountForCopy.getClient().getId()));
        service.edit(accountForEdit);
        return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAccount(@RequestBody Account entity) {
        User currentUser = getCurrentUser();

        if (entity.getClient().getId().equals(currentUser.getClient().getId())) {
            service.save(entity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN) || currentUser.getRole().equals(Role.ROLE_SUPERADMIN)) {
            service.save(entity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.getByUsername(currentPrincipalName);
    }

    private ResponseEntity<Account> checkEntityAndRole(Account account) {
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User currentUser = getCurrentUser();

        if (currentUser.getClient().getAccounts().stream()
                .anyMatch(acc -> acc.getId().equals(account.getId())) &&
                currentUser.getRole().equals(Role.ROLE_USER)) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else if (currentUser.getRole().equals(Role.ROLE_ADMIN) || currentUser.getRole().equals(Role.ROLE_SUPERADMIN)) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity<List<Account>> checkListOfEntityAndRole(List<Account> accounts) {
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Account> allowedAccounts = new ArrayList<>();
        for (Account account : accounts) {
            ResponseEntity<Account> responseEntity = checkEntityAndRole(account);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                allowedAccounts.add(account);
            }
        }

        if (allowedAccounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(allowedAccounts, HttpStatus.OK);
    }

}
