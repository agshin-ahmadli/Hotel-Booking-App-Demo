package org.booking;

import org.booking.entity.Client;
import org.booking.entity.Hotel;
import org.booking.entity.Room;
import org.booking.repository.ClientRepository;
import org.booking.repository.ClientRepositoryImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ClientRepository clientRepository = new ClientRepositoryImpl();
        AccountService service = new AccountServiceImpl(clientRepository);

        // Interface for Administrator
        String prompt = """
                -----------Interface for Administrator-----------
                Enter total rooms:
                """;
        System.out.print(prompt);
        int totalRooms = scanner.nextInt();
        List<Room> roomList = service.createRooms(totalRooms);

        // Interface for Clients
        String userPrompt = """
                ---------------Interface for Clients---------------
                Please register to check out all available rooms
                """;
        System.out.println(userPrompt);
        System.out.print("Enter client Id: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        service.createClient(clientId, clientName);

        // find all users
        List<Client> clients = service.findAllClients();

        // Creating Hotel
        Hotel hotel1 = new Hotel("Baku, Azerbaijan", clients, roomList);

        // Pagination
        String paginationFormat = """
                To get started, please enter the number of rooms you’d like to display per page
                Enter here:
                """;
        System.out.println(paginationFormat);
        int roomPerPage = scanner.nextInt();
        service.paginationRoomForClientRequest(roomPerPage, roomList);

        System.out.println();
        System.out.println();

        // Booking
        String bookingPrompt = """
                Ready to secure your room?
                Please enter your preferred Room ID and your Client ID to proceed with the booking:
                """;
        System.out.println(bookingPrompt);
        System.out.print("Room ID: ");
        int roomId = scanner.nextInt();
        System.out.print("Client ID: ");
        int clientID = scanner.nextInt();
        service.createBookingForClient(roomList, clients, roomId, clientID);

        System.out.println();

        // Cancel Booking
        String cancelBookingPrompt = """
                Are you sure you want to cancel your room booking? If so, please enter your
                Room ID and Client ID to confirm the cancellation. Otherwise, feel free to keep your
                reservation!
                """;
        System.out.println(cancelBookingPrompt);
        System.out.print("Room ID: ");
        int roomIdToCancel = scanner.nextInt();
        System.out.print("Client ID: ");
        int clientIdToCancel = scanner.nextInt();
        service.cancelExistingBooking(roomList, clients, roomIdToCancel, clientIdToCancel);

        scanner.close();
    }
}
