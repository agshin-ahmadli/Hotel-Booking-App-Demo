import org.booking.config.ConfigUtil;
import org.booking.dataformat.SerializationUtil;
import org.booking.repository.RoomRepository;
import org.booking.repository.RoomRepositoryImpl;
import org.booking.service.HotelService;
import org.booking.service.HotelServiceImpl;
import org.booking.entity.Client;
import org.booking.entity.Hotel;
import org.booking.entity.Room;
import org.booking.repository.ClientRepository;
import org.booking.repository.ClientRepositoryImpl;
import org.booking.service.RoomVerificationService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ClientRepository clientRepository = new ClientRepositoryImpl();
        RoomVerificationService roomVerificationService = new RoomVerificationService();
        ConfigUtil configUtil = new ConfigUtil();

        HotelService service = new HotelServiceImpl( configUtil, clientRepository, roomVerificationService);


        // Interface for Administrator
        String adminPrompt = """
                ----------- interface for administrator -----------
                enter total rooms:
                """;
        System.out.print(adminPrompt);
        int totalRooms = scanner.nextInt();
        List<Room> roomList = service.createRooms(totalRooms);



        // Interface for Clients
        String clientPrompt = """
                --------------- Interface for Clients ---------------
                Please register to check out all available rooms
                """;
        System.out.println(clientPrompt);
        System.out.print("Enter client Id: ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Clear the newline
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        service.createClient(clientId, clientName);



        // Find all clients
        List<Client> clients = service.findAllClients();



        // Create Hotel
        Hotel hotel = new Hotel("Baku, Azerbaijan", clients, roomList);



        // Save and load hotel state
       SerializationUtil.saveState(hotel, "hotelState.ser");
       Hotel loadedHotel = SerializationUtil.loadState("hotelState.ser");



        // Pagination
        String paginationPrompt = """
                To get started, please enter the number of rooms you’d like to display per page:
                """;
        System.out.print(paginationPrompt);
        int roomsPerPage = scanner.nextInt();
        service.paginationRoomForClientRequest(roomsPerPage, roomList);

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
        String cancelPrompt = """
                Are you sure you want to cancel your room booking? If so, please enter your
                Room ID and Client ID to confirm the cancellation. Otherwise, feel free to keep your
                reservation!
                """;
        System.out.println(cancelPrompt);
        System.out.print("Room ID: ");
        int roomIdToCancel = scanner.nextInt();
        System.out.print("Client ID: ");
        int clientIdToCancel = scanner.nextInt();
        service.cancelExistingBooking(roomList, clients, roomIdToCancel, clientIdToCancel);

        scanner.close();
        service.changeHotelStatus(hotel, "closed");
    }
}
