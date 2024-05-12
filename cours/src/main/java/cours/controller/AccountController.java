package cours.controller;

import cours.entity.Account;
import cours.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
public class AccountController extends AbstractController<Account> {
    @Autowired
    private AccountService service;
    @Override
    public AccountService getService() {
        return service;
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Account> getTransactionsByType(@PathVariable String type) {
        Account account = service.readByAccountType(type);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, headers, HttpStatus.OK);
    }
}
