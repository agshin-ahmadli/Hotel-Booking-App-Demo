package org.booking.repository;
import org.booking.entity.Client;

import java.util.*;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepository {

    Map<Integer, Client> clients = new HashMap<>();
    @Override
    public Client saveClient(Client client) {
        if(!clients.containsKey(client.getClientId())){
            clients.put(client.getClientId(), client);
        }
        return client;
    }
    @Override
    public List<Client> clientList(){
        return new ArrayList<>(clients.values());
    }
}
