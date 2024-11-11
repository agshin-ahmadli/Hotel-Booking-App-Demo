package org.booking;
import static org.booking.entity.Status.AVAILABLE;
import static org.booking.entity.Status.RESERVED;
import static org.junit.jupiter.api.Assertions.*;
import org.booking.entity.Client;
import org.booking.entity.Room;
import org.booking.repository.ClientRepository;
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
class AccountServiceImplTest {
    @Mock
    ClientRepository repository;
    @InjectMocks
    AccountServiceImpl service;
    int clientId;
    String name;

    @BeforeEach
    void init(){
        clientId = 1;
        name = "Will";
    }
    @DisplayName("Client object created")
    @Test
    void testCreateClient_whenDetailsProvided_returnsListOfClientObjects(){
        Mockito.when(repository.saveClient(Mockito.any(Client.class))).thenReturn(new Client(clientId, name, new ArrayList<>()));
        Client client = service.createClient(clientId, name);
        String resultClientName = client.getName();
        Integer resultClientId = client.getClientId();

        assertNotNull(client, "The createClient() method should not have returned null");
        assertEquals(name, resultClientName, "Client's name is incorrect");
        assertNotNull(resultClientId, "Client Id is missing");
        Mockito.verify(repository).saveClient(Mockito.any(Client.class));

    }

    @DisplayName("Empty name causes correct exception")
    @Test
    void testCreateClient_whenNameOrIdIsEmpty_throwIllegalArgumentException(){
        String name = "";
        String expectedExceptionMessage = "Client's name is empty";

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () ->{
            service.createClient(clientId,name);
        }, "Empty name should have caused an Illegal Argument Exception");

        assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(),
                "Exception error message is incorrect");
    }

    @DisplayName("Room object created")
    @Test
    void testCreateRoom_whenDetailsProvided_returnsListOfRoomObjects(){
       Scanner scanner = Mockito.mock(Scanner.class);
       RoomPriceAndSorting roomPriceAndSorting = new RoomPriceAndSorting(scanner);
       Mockito.when(scanner.nextDouble()).thenReturn(100.0,200.0, 300.0);

       int numberOfRooms = 3;
       List<Room>rooms = roomPriceAndSorting.setPriceForRoomId(numberOfRooms);

       assertEquals(numberOfRooms, rooms.size(), "The number of rooms created should match the input");

       assertEquals(100.0, rooms.get(0).getRoomPrice(), "First room price should be 100.0 $");
       assertEquals(200.0, rooms.get(1).getRoomPrice(), "Second room price should be 200.0 $");
       assertEquals(300, rooms.get(2).getRoomPrice(), "Third room price should be 300 $");

       assertEquals(AVAILABLE, rooms.get(0).getStatus(), "First room status should be AVAILABLE");
       assertEquals(AVAILABLE, rooms.get(1).getStatus(), "Second room status should be AVAILABLE");
       assertEquals(AVAILABLE, rooms.get(2).getStatus(), "Third room status should be AVAILABLE");
       Mockito.verify(scanner,Mockito.times(3)).nextDouble();
    }

    @Test
    void testSuccessfulBooking(){
        Room room = new Room(1, 200.0, AVAILABLE);
        List<Room>rooms = new ArrayList<>();
        rooms.add(room);
        Client client = new Client(1, "John Doe", rooms);

        service.createBookingForClient(rooms, List.of(client), 1, 1);

        assertEquals(RESERVED, room.getStatus(), "Room status should be RESERVED");
        assertEquals(1, client.getRoomList().size(), "Client should have only one room booked");
        assertEquals(room, client.getRoomList().get(0));
    }
    @Test
    void testSuccessfulCancelling(){
        Room room = new Room(1, 200.0, RESERVED);
        List<Room>rooms = new ArrayList<>();
        rooms.add(room);
        Client client = new Client(1, "John Doe", rooms);

        service.cancelExistingBooking(rooms, List.of(client), 1, 1);

        assertEquals(AVAILABLE, room.getStatus(), "Room status should be AVAILABLE");
        assertEquals(0, client.getRoomList().size(), "Client should cancel only one room booked");
        assertTrue(client.getRoomList().isEmpty());
    }
}