package org.booking.service;

import org.booking.entity.Room;
import org.booking.entity.Status;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class RoomPriceAndSorting {

    private final Scanner scanner;

    public RoomPriceAndSorting(Scanner scanner) {
        this.scanner = scanner;
    }

    public List<Room> setPricesForRooms(int totalRooms) {
        List<Room> rooms = new ArrayList<>();

        for (int i = 1; i <= totalRooms; i++) {
            System.out.printf("Enter price ($) for room Id: %d: ", i);
            double roomPrice = scanner.nextDouble();

            Room room = new Room(i, roomPrice, Status.AVAILABLE);
            rooms.add(room);
        }

        sortRoomsByPrice(rooms);
        return rooms;
    }

    private void sortRoomsByPrice(List<Room> rooms) {
        rooms.sort(Comparator.comparingDouble(Room::getRoomPrice));
    }
}
