package org.booking.repository;
import org.booking.entity.Client;
import java.util.List;

public interface ClientRepository {

    Client saveClient(Client client);
    List<Client> clientList();
}
