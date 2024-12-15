package cours.service.impl;

import cours.entity.Client;
import cours.entity.User;
import cours.repository.ClientRepository;
import cours.repository.UserRepository;
import cours.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Client read(Long id) {
        return clientRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
    @Override
    public List<Client> read() {
        return clientRepository.findAll();
    }

    @Override
    public void save(Client entity) {
        if (entity.getUser () != null) {
            Long id = entity.getUser ().getId();
            User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User  not found"));
            entity.setUser (user);
        }
        clientRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        clientRepository.delete(client);
    }

    @Override
    public Client readByName(String clientName) {
        return clientRepository.findByName(clientName);
    }

    @Override
    public void edit(Client entity) {
        Client client = clientRepository.findById(entity.getId()).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        client.setName(entity.getName());
        client.setSurname(entity.getSurname());
        client.setPhone(entity.getPhone());
        if (entity.getUser () != null) {
            User user = userRepository.findById(entity.getUser ().getId()).orElseThrow(() -> new IllegalArgumentException("User  not found"));
            client.setUser (user);
        }
        clientRepository.save(client);
    }
}
