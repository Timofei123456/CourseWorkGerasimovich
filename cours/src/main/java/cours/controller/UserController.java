package cours.controller;

import cours.entity.Role;
import cours.entity.User;
import cours.service.Service;
import cours.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        User user = service.read(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = service.getByUsername(currentPrincipalName);

        if (currentUser.getId().equals(user.getId()) && currentUser.getRole().equals(Role.ROLE_USER)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else if(currentUser.getRole().equals(Role.ROLE_ADMIN)){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    public Service<User> getService() {
        return service;
    }
}
