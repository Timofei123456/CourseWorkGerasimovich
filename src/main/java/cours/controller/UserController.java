package cours.controller;

import cours.entity.Role;
import cours.entity.User;
import cours.service.Service;
import cours.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> entities = service.read();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

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
        else if(currentUser.getRole().equals(Role.ROLE_ADMIN) || currentUser.getRole().equals(Role.ROLE_SUPERADMIN)){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        User user = service.getByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser  = service.getByUsername(currentPrincipalName);

        if (currentUser .getRole().equals(Role.ROLE_ADMIN) || currentUser .getRole().equals(Role.ROLE_SUPERADMIN)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if (currentUser .getUsername().equals(username) && currentUser .getRole().equals(Role.ROLE_USER)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        User user = service.read(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User currentUser = service.getByUsername(currentPrincipalName);

        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updatedUser.setId(currentUser.getId());
        service.edit(updatedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Service<User> getService() {
        return service;
    }
}
