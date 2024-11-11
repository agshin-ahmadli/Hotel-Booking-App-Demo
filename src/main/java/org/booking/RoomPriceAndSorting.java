package org.booking;

import org.booking.entity.Room;
import org.booking.entity.Status;

import java.util.*;
import java.util.stream.Collectors;

public class RoomPriceAndSorting {
   Scanner scanner;
    public RoomPriceAndSorting(Scanner scanner) {
        this.scanner = scanner;
    }

    List<Room>setPriceForRoomId(int totalRooms) {
        List<Room> rooms = new ArrayList<>();


        for (int i = 1; i <= totalRooms; ++i) {
            String Id = String.valueOf(i);
            System.out.printf("Enter price ($) for room Id: %s ", Id);
            double roomPrice = scanner.nextDouble();
            Status status = Status.AVAILABLE;
            Room room = new Room(i, roomPrice, status);
            rooms.add(room);
        }
        sortRoomsByPrice(rooms);
        return rooms;
    }

    void sortRoomsByPrice(List<Room>rooms) {
        rooms.sort(Comparator.comparingDouble(Room::getRoomPrice));
    }
}