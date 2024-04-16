package cours.service;

import cours.entity.Client;

public interface ClientService extends Service<Client>{

    Client readByName(String clientName);
}
