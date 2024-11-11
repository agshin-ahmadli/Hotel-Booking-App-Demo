package org.booking;
import org.booking.entity.Client;
import org.booking.entity.Room;
import org.booking.repository.ClientRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.booking.entity.Status.AVAILABLE;
import static org.booking.entity.Status.RESERVED;

public class AccountServiceImpl implements AccountService {
    Scanner scanner = new Scanner(System.in);
    ClientRepository clientRepository;
    public AccountServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public Client createClient(Integer clientId, String clientName) {
        if(clientName.trim().isEmpty()){
            throw new IllegalArgumentException("Client's name is empty");
        }
        return clientRepository.saveClient(new Client(clientId, clientName, new ArrayList<>()));
    }

    @Override
    public List<Room> createRooms(int totalRooms) {
        RoomPriceAndSorting roomPriceAndSorting = new RoomPriceAndSorting(scanner);
        return roomPriceAndSorting.setPriceForRoomId(totalRooms);
    }
    @Override
    public void paginationRoomForClientRequest(int roomPerPage, List<Room> roomList) {
        int numberOfRooms = roomList.size();
        int totalPage = (int) Math.ceil((double) numberOfRooms / roomPerPage);
        int currentPage = 1;
        boolean continueNavigation = true;

        while (continueNavigation) {
            int start = (currentPage - 1) * roomPerPage;
            int end = Math.min(start + roomPerPage, numberOfRooms);

            System.out.println("\nPage %d of %d".formatted(currentPage, totalPage));

            for (int i = start; i < end; ++i) {
                System.out.println("""
                        Room ID: %s,
                        Status: %s,
                        Price: %s
                        """.formatted(
                        roomList.get(i).getRoomId(),
                        roomList.get(i).getStatus().name(),
                        roomList.get(i).getRoomPrice()
                ));
            }

            System.out.println("Enter 'n' for next, 'p' for previous");
            String action = scanner.next();
            if (currentPage >= totalPage && action.equalsIgnoreCase("n")) {
                continueNavigation = false;
            }

            if (action.equalsIgnoreCase("n")) {
                if (currentPage < totalPage) {
                    currentPage++;
                } else {
                    System.out.printf("You are on the last page: %d", currentPage);
                }
            } else if (action.equalsIgnoreCase("p")) {
                if (currentPage > 1) {
                    currentPage--;
                } else {
                    System.out.printf("You are on the first page: %d", currentPage);
                }
            }
        }
    }
    @Override
    public void createBookingForClient(List<Room> rooms, List<Client> clients, int roomId, int clientId) {
        Room verifiedRoom = verifyRoomById(rooms, roomId);
        Client verifiedClient = verifyClientById(clients, clientId);
        List<Room> clientsReservations = new ArrayList<>();

        if (verifiedRoom != null && verifiedClient != null) {
            if (verifiedRoom.getStatus() == AVAILABLE) {
                clientsReservations.add(verifiedRoom);
                verifiedClient.setRoomList(clientsReservations);
                verifiedRoom.setStatus(RESERVED);
                System.out.println("Booking Confirmed! Your reservation was successful. Get ready for an amazing stay!");
            } else {
                System.out.printf("Room with %d not found ", roomId);
            }
        }
    }

    @Override
    public void cancelExistingBooking(List<Room> rooms, List<Client> clients, int roomId, int clientId) {
        try {
            Room verifiedRoom = verifyRoomById(rooms, roomId);
            Client verifiedClient = verifyClientById(clients, clientId);

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
                        "Please double-check the IDs and try again!", roomId, clientId);
            }
        } catch (RuntimeException e) {
            System.out.println("An error occurred while processing your request: " + e.getMessage());
        }
    }

    public Room verifyRoomById(List<Room> rooms, int roomId) {
        return rooms.stream().filter(room -> room.getRoomId() == roomId).findFirst()
                .orElseThrow(() -> new RuntimeException("Room with Id " + roomId + " not found"));
    }
    public Client verifyClientById(List<Client> clients, int clientId) {
        return clients.stream().filter(client -> client.getClientId() == clientId).findFirst()
                .orElseThrow(() -> new RuntimeException("Client with Id " + clientId + " not found"));
    }
    @Override
    public List<Client> findAllClients() {
        return clientRepository.clientList();
    }

}
