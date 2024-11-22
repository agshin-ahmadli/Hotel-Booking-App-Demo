package org.booking.service;
import org.booking.models.Hotel;
import org.booking.models.Ticket;
import org.booking.models.Client;

import org.booking.models.Room;

import java.util.List;

public interface HotelService {

    void registerRooms(Room room);

    void addToHotel(String location, Room room);

    List<Room> getRooms();

    List<Room> getAvailableRooms(int pageNumber, int pageSize);

    Ticket checkIn(Client client, Long roomId, int stayLength);







}

