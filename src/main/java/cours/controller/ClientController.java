package cours.controller;

import cours.entity.Client;
import cours.entity.Role;
import cours.entity.User;
import cours.service.ClientService;
import cours.service.UserService;
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
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService service;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> entities = service.read();
        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable long clientId) {
        Client client = service.read(clientId);
        return checkEntityAndRole(client, clientId);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Client> getByClientName(@PathVariable String name) {
        Client client = service.readByName(name);
        return checkEntityAndRole(client, client.getId());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putClient(@RequestBody Client entity) {
        service.edit(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postClient(@RequestBody Client entity) {
            service.save(entity);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientDyId(@PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.getByUsername(currentPrincipalName);
    }

    private ResponseEntity<Client> checkEntityAndRole(Client client, Long clientId) {
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User currentUser  = getCurrentUser ();

        Client userClient = currentUser .getClient();
        if (userClient != null) {
            if (userClient.getId().equals(clientId) && currentUser .getRole().equals(Role.ROLE_USER)) {
                return new ResponseEntity<>(client, HttpStatus.OK);
            }
        }

        if (currentUser .getRole().equals(Role.ROLE_ADMIN) || currentUser .getRole().equals(Role.ROLE_SUPERADMIN)) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}