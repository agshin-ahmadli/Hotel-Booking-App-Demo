import org.booking.controller.HotelController;
import org.booking.models.Status;
import org.booking.repository.HotelRepository;
import org.booking.repository.RoomRepository;
import org.booking.repository.TicketRepository;
import org.booking.repository.impl.HotelRepositoryImpl;
import org.booking.repository.impl.RoomRepositoryImpl;
import org.booking.repository.impl.TicketRepositoryImpl;
import org.booking.service.HotelService;
import org.booking.service.HotelServiceImpl;
import org.booking.models.Client;
import org.booking.models.Hotel;
import org.booking.models.Room;
import org.booking.repository.ClientRepository;
import org.booking.repository.impl.ClientRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClientRepository clientRepository = new ClientRepositoryImpl();
        RoomRepository roomRepository = new RoomRepositoryImpl();
        HotelRepository hotelRepository = new HotelRepositoryImpl();
        TicketRepository ticketRepository = new TicketRepositoryImpl();


    HotelService hotelService =  new HotelServiceImpl(clientRepository, roomRepository,
                                    hotelRepository, ticketRepository);


        HotelController controller = new HotelController(hotelService);
        controller.manageHotel();










    }
}
