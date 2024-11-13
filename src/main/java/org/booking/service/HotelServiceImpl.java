package org.booking.service;

import org.booking.config.ConfigUtil;
import org.booking.entity.Client;
import org.booking.entity.Hotel;
import org.booking.entity.Room;
import org.booking.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.booking.entity.Status.AVAILABLE;
import static org.booking.entity.Status.RESERVED;

public class HotelServiceImpl implements HotelService {

    private final Scanner scanner = new Scanner(System.in);
    private final ConfigUtil configUtil;
    private final ClientRepository clientRepository;
    private final RoomVerificationService roomVerificationService;

    public HotelServiceImpl(ConfigUtil configUtil, ClientRepository clientRepository,
                            RoomVerificationService roomVerificationService) {
        this.configUtil = configUtil;
        this.clientRepository = clientRepository;
        this.roomVerificationService = roomVerificationService;
    }



    @Override
    public Client createClient(Integer clientId, String clientName) {
        if (clientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Client's name is empty");
        }
        return clientRepository.saveClient(new Client(clientId, clientName, new ArrayList<>()));
    }



    @Override
    public List<Room> createRooms(int totalRooms) {
        RoomPriceAndSorting roomPriceAndSorting = new RoomPriceAndSorting(scanner);
        return roomPriceAndSorting.setPricesForRooms(totalRooms);
    }



    @Override
    public void paginationRoomForClientRequest(int roomsPerPage, List<Room> roomList) {
        PaginationHandler handler = new PaginationHandler(scanner);
        int totalRooms = roomList.size();
        int totalPages = handler.calculateTotalPages(roomsPerPage, totalRooms);
        int currentPage = 1;
        boolean continueNavigation = true;

        while (continueNavigation) {
            handler.displayRoomPage(currentPage, totalPages, roomList, roomsPerPage);
            String action = handler.getNavigationAction();

            if (handler.isNextPageAction(currentPage, totalPages, action)) {
                currentPage++;
            } else if (handler.isPreviousPageAction(currentPage, action)) {
                currentPage--;
            } else {
                continueNavigation = false;
            }
        }
    }



    @Override
    public void createBookingForClient(List<Room> rooms, List<Client> clients, int roomId, int clientId) {
        Room verifiedRoom = roomVerificationService.verifyRoomById(rooms, roomId);
        Client verifiedClient = roomVerificationService.verifyClientById(clients, clientId);

        if (verifiedRoom != null && verifiedClient != null) {
            if (verifiedRoom.getStatus() == AVAILABLE) {
                verifiedRoom.setStatus(RESERVED);
                verifiedClient.getRoomList().add(verifiedRoom);
                System.out.println("Booking Confirmed! Your reservation was successful. Get ready for an amazing stay!");
            } else {
                System.out.printf("Room with ID %d not found%n", roomId);
            }
        }
    }



    @Override
    public void cancelExistingBooking(List<Room> rooms, List<Client> clients, int roomId, int clientId) {
        try {
            Room verifiedRoom = roomVerificationService.verifyRoomById(rooms, roomId);
            Client verifiedClient = roomVerificationService.verifyClientById(clients, clientId);

            if (verifiedRoom != null && verifiedClient != null) {
                if (verifiedRoom.getStatus() == RESERVED) {
                    verifiedRoom.setStatus(AVAILABLE);
                    verifiedClient.getRoomList().removeIf(room -> room.getRoomId() == roomId);
                    System.out.println("Booking Canceled Successfully! Your reservation has been removed. " +
                            "We hope to assist you with future bookings whenever you're ready!");
                } else {
                    System.out.println("Oops! We couldn't locate the room you’ve booked. " +
                            "Please double-check your details or try again.");
                }
            } else {
                System.out.printf("Oops! We couldn't find the Room with ID %d or the Client with ID %d. " +
                        "Please double-check the IDs and try again!%n", roomId, clientId);
            }
        } catch (RuntimeException e) {
            System.out.println("An error occurred while processing your request: " + e.getMessage());
        }
    }



    @Override
    public List<Client> findAllClients() {
        return clientRepository.clientList();
    }


    @Override
    public void changeHotelStatus(Hotel hotel, String newStatus) {
        if (configUtil.isHotelStatusChangeAllowed()) {
            hotel.setStatus(newStatus);
            System.out.println("Hotel status changed.");
        } else {
            System.out.println("Hotel status change is disabled by configuration.");
        }
    }
}

