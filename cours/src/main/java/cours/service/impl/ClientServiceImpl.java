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
    public List<Client> readByUser_Id(Long userId) {
        return clientRepository.findByUser_Id(userId);
    }
    @Override
    public void save(Client entity) {
        User user = entity.getUser();
        Long id = user.getId();
        user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        entity.setUser(user);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        Client client = clientRepository.findById(id).get();
        User user = client.getUser();
        userRepository.save(user);
    }

    @Override
    public Client readByName(String clientName) {
        return clientRepository.findByName(clientName);
    }

    @Override
    public void edit(Client entity) {
        User user = entity.getUser();
        Long uId = user.getId();
        Long cId = entity.getId();
        user = userRepository.findById(uId).orElseThrow(IllegalArgumentException::new);
        Client client = clientRepository.findById(cId).orElseThrow(IllegalArgumentException::new);
        client.setUser(user);
        client.setName(entity.getName());
        clientRepository.save(client);
    }
}
