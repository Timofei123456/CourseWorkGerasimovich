package cours.service;

import cours.entity.Client;

import java.util.List;

public interface ClientService extends Service<Client>{
    List<Client> readByUser_Id(Long userId);
    Client readByName(String clientName);
}
