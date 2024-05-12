package cours.controller;

import cours.entity.Client;
import cours.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client")
public class ClientController extends AbstractController<Client> {
    @Autowired
    private ClientService service;
    @Override
    public ClientService getService() {
        return service;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Client> getClientsByClientName(@PathVariable String name) {
        Client client = service.readByName(name);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, headers, HttpStatus.OK);
    }
}