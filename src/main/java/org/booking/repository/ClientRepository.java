package org.booking.repository;
import org.booking.models.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    void saveClient(Client client);

    List<Client> clientList();

    Optional<Client> findById(Long clientId);
}
