package org.booking.repository;
import org.booking.models.Room;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    void save(Room room);

    List<Room> findAll();

    void updateRoom(Room room);

    List<Room> getRoomsWithPagination(int pageNumber, int pageSize);

    void deleteById(long id);

    Optional<Room>findAvailableRoomById(long id);
}
