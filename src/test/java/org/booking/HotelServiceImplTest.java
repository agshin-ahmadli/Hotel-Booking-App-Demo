package org.booking;

import static org.booking.models.Status.AVAILABLE;
import static org.booking.models.Status.RESERVED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.booking.config.ConfigUtil;
import org.booking.models.Client;
import org.booking.models.Hotel;
import org.booking.models.Room;
import org.booking.models.Status;
import org.booking.repository.ClientRepository;
import org.booking.service.HotelServiceImpl;
import org.booking.service.PaginationHandler;
import org.booking.service.RoomPriceAndSorting;
import org.booking.service.RoomVerificationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    private ConfigUtil configUtil;

    @Mock
    private RoomVerificationService roomVerificationService;

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private HotelServiceImpl service;

    private int clientId;
    private String clientName;

    @BeforeEach
    void setUp() {
        clientId = 1;
        clientName = "Will";
    }

    @DisplayName("Create client with valid details")
    @Test
    void createClient_withValidDetails_shouldReturnClientObject() {
        when(repository.saveClient(Mockito.any(Client.class)))
                .thenReturn(new Client(clientId, clientName, new ArrayList<>()));

        Client client = service.createClient(clientId, clientName);

        assertNotNull(client, "The createClient() method should not return null");
        assertEquals(clientName, client.getName(), "Client's name does not match expected value");
        assertEquals(clientId, client.getClientId(), "Client ID does not match expected value");

        Mockito.verify(repository).saveClient(Mockito.any(Client.class));
    }

    @DisplayName("Empty client name triggers exception")
    @Test
    void createClient_withEmptyName_shouldThrowIllegalArgumentException() {
        String emptyName = "";
        String expectedExceptionMessage = "Client's name is empty";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createClient(clientId, emptyName),
                "Empty name should trigger IllegalArgumentException"
        );

        assertEquals(expectedExceptionMessage, exception.getMessage(), "Exception message does not match expected value");
    }

    @DisplayName("Create room objects with valid prices")
    @Test
    void createRoom_withValidDetails_shouldReturnListOfRooms() {
        Scanner mockScanner = Mockito.mock(Scanner.class);
        RoomPriceAndSorting roomPriceAndSorting = new RoomPriceAndSorting(mockScanner);

        when(mockScanner.nextDouble()).thenReturn(100.0, 200.0, 300.0);

        int numberOfRooms = 3;
        List<Room> rooms = roomPriceAndSorting.setPricesForRooms(numberOfRooms);

        assertEquals(numberOfRooms, rooms.size(), "Number of rooms created should match input");
        assertEquals(100.0, rooms.get(0).getRoomPrice(), "First room price should be 100.0");
        assertEquals(200.0, rooms.get(1).getRoomPrice(), "Second room price should be 200.0");
        assertEquals(300.0, rooms.get(2).getRoomPrice(), "Third room price should be 300.0");

        for (Room room : rooms) {
            assertEquals(AVAILABLE, room.getStatus(), "Room status should be AVAILABLE by default");
        }

        Mockito.verify(mockScanner, Mockito.times(numberOfRooms)).nextDouble();
    }

    @DisplayName("Successful room booking")
    @Test
    void createBookingForClient_withAvailableRoom_shouldUpdateRoomStatusToReserved() {
        Room room = new Room(1, 200.0, AVAILABLE);
        List<Room> rooms = List.of(room);
        Client client = new Client(1, "Norton", new ArrayList<>());
        List<Client> clients = List.of(client);
        int roomId = 1;
        int clientId = 1;

        when(roomVerificationService.verifyRoomById(rooms, roomId)).thenReturn(room);
        when(roomVerificationService.verifyClientById(clients, clientId)).thenReturn(client);

        service.createBookingForClient(rooms, clients, roomId, clientId);

        assertEquals(RESERVED, room.getStatus(), "Room status should be RESERVED after booking");
        assertEquals(1, client.getRoomList().size(), "Client should have one room booked");
        assertEquals(room, client.getRoomList().get(0), "Booked room does not match expected room");
    }

    @DisplayName("Successful room cancellation")
    @Test
    void cancelExistingBooking_withReservedRoom_shouldUpdateRoomStatusToAvailable() {
        Room room = new Room(1, 200.0, Status.RESERVED);
        List<Room> rooms = new ArrayList<>(List.of(room));
        Client client = new Client(1, "Norton", new ArrayList<>(List.of(room)));
        List<Client> clients = new ArrayList<>(List.of(client));

        int roomId = 1;
        int clientId = 1;

        when(roomVerificationService.verifyRoomById(rooms, roomId)).thenReturn(room);
        when(roomVerificationService.verifyClientById(clients, clientId)).thenReturn(client);

        service.cancelExistingBooking(rooms, clients, roomId, clientId);

        assertEquals(AVAILABLE, room.getStatus(), "Room status should be AVAILABLE after cancellation");
        assertTrue(client.getRoomList().isEmpty(), "Client's room list should be empty after cancellation");
    }

    @DisplayName("Should return all clients successfully")
    @Test
    void findAllClients_shouldReturnListOfAllClients() {
        List<Client> clients = new ArrayList<>(List.of(new Client(), new Client()));
        int totalClients = clients.size();

        when(repository.clientList()).thenReturn(clients);
        List<Client> clientList = service.findAllClients();

        assertEquals(totalClients, clientList.size(), "Total number of clients should be 2");
    }

    @DisplayName("Hotel status should be updated to the new status successfully")
    @Test
    void changeHotelStatus_withValidHotelAndNewStatus_shouldUpdateHotelStatus() {
        Hotel hotel = new Hotel();
        String status = "closed";

        when(configUtil.isHotelStatusChangeAllowed()).thenReturn(true);

        service.changeHotelStatus(hotel, status);

        assertEquals(status, hotel.getStatus(), "Hotel status should set to 'closed'");
    }

    @DisplayName("Should calculate total pages and determine if next page action is available")
    @Test
    void pagination_calculateTotalPagesAndCheckNextPageAction() {
        Scanner mockedScanner = Mockito.mock(Scanner.class);
        PaginationHandler paginationHandler = new PaginationHandler(mockedScanner);

        int roomsPerPage = 3;
        int totalRooms = 25;
        int expectedTotalPages = 9;
        int currentPage = 1;
        String action = "N";

        int calculatedTotalPages = paginationHandler.calculateTotalPages(roomsPerPage, totalRooms);
        boolean nextPageAvailable = paginationHandler.isNextPageAction(currentPage, calculatedTotalPages, action);

        assertEquals(expectedTotalPages, calculatedTotalPages, "The calculated total pages should match the expected total pages.");
        assertTrue(nextPageAvailable, "The action should indicate that the next page is available.");
    }
}
