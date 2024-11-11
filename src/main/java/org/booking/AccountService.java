package org.booking;
import org.booking.entity.Client;

import org.booking.entity.Room;

import java.util.List;

public interface AccountService {
    List<Room> createRooms(int totalRooms);
    Client createClient(Integer clientId, String clientName);
    void paginationRoomForClientRequest(int numberOfRooms, List<Room>roomList);
    void createBookingForClient(List<Room> rooms, List<Client> clients, int roomId, int clientId);
    void cancelExistingBooking(List<Room> rooms, List<Client> clients, int roomId, int clientId);
    List<Client>findAllClients();
}

