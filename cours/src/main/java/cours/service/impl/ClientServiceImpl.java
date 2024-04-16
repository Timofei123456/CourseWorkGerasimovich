package cours.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cours.entity.Client;
import cours.repository.ClientRepository;
import cours.service.ClientService;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

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
        clientRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client readByName(String clientName) {
        return clientRepository.findByName(clientName);
    }

    @Override
    public void edit(Client entity) {
        Client client = clientRepository.findById(entity.getId()).orElseThrow(IllegalArgumentException::new);
        client.setName(entity.getName());
        client.setSurname(entity.getSurname());
        client.setEmail(entity.getEmail());
        client.setPhone(entity.getPhone());
        clientRepository.save(client);
    }
}