package org.booking.controller;

import org.booking.models.*;
import org.booking.service.HotelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelController {

    private final HotelService hotelService;
    private final Scanner scanner;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
        this.scanner = new Scanner(System.in);
    }

    public void manageHotel() {
        // Price input for room
        System.out.print("""
                Hotel Manager Panel:
                Please enter the price for the room:
                """);
        double price = scanner.nextDouble();
        scanner.nextLine();


        System.out.print("""
                State room Status:
                If it is AVAILABLE, press 'A'.
                If it is RESERVED, press 'B':
                """);
        String status = scanner.nextLine();
        Status roomStatus = null;

        switch (status) {
            case "A" -> roomStatus = Status.AVAILABLE;
            case "B" -> roomStatus = Status.RESERVED;
            default -> System.out.println("Invalid status input.");
        }

        // Pagination
        System.out.println("Fetching available rooms (Page 1, Size 10):");
        List<Room> availableRooms = hotelService.getAvailableRooms(1, 10);
        availableRooms.forEach(room -> System.out.println(room));

        // Room registration
        Room room = new Room();
        room.setRoomPrice(price);
        room.setStatus(roomStatus);

        hotelService.registerRooms(room);
        System.out.println("Room registered successfully!");

        // Booking process
        System.out.print("""
                In order to book, please fill in the blank:
                """);

        // Input for booking
        System.out.print("Enter preferred roomId: ");
        Long roomId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();

        System.out.print("Enter stay length (days): ");
        int stayLength = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter hotel location: ");
        String hotelLocation = scanner.nextLine();


        Hotel hotel = new Hotel();
        hotel.setHotelLocation(hotelLocation);

        Client client = new Client();
        client.setName(clientName);


        hotelService.checkIn(client, roomId, stayLength);


        System.out.println("Booking successful!");
    }
}