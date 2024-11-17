package org.booking.repository;

import org.booking.entity.Room;

import java.util.List;

public interface RoomRepository {
    void save(Room room);
    List<Room> findAll();
}
