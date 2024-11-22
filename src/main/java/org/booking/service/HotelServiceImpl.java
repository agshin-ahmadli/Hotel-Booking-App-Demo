package org.booking.service;

import org.booking.models.*;
import org.booking.repository.ClientRepository;
import org.booking.repository.HotelRepository;
import org.booking.repository.RoomRepository;
import org.booking.repository.TicketRepository;

import java.time.LocalDate;
import java.util.List;

public class HotelServiceImpl implements HotelService {

    private final ClientRepository clientRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final TicketRepository ticketRepository;


    public HotelServiceImpl(ClientRepository clientRepository, RoomRepository roomRepository, HotelRepository hotelRepository, TicketRepository ticketRepository) {
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public void registerRooms(Room room) {
        roomRepository.save(room);
    }


    @Override
    public void addToHotel(String location, Room room) {
        List<Room> rooms = getRooms();
        hotelRepository.addHotel(new Hotel(location, rooms));
    }


    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }


    @Override
    public List<Room> getAvailableRooms(int pageNumber, int pageSize) {
        return roomRepository.getRoomsWithPagination(pageNumber, pageSize);
    }


    @Override
    public Ticket checkIn(Client client, Long roomId, int stayLength) {

        clientRepository.saveClient(client);

        Room room = roomRepository.findAvailableRoomById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found or already reserved with ID: " + roomId));

        Ticket ticket = new Ticket();
        ticket.setClient(client);
        ticket.setCheckInDate(LocalDate.now());
        ticket.setCheckOutDate(LocalDate.now().plusDays(stayLength));

        ticket.getRooms().add(room);
        room.setStatus(Status.RESERVED);
        room.setClient(client);
        roomRepository.updateRoom(room);

        ticketRepository.addBookingTicket(ticket);
        return ticket;
    }
}









