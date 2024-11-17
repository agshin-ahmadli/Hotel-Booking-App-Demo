package org.booking.repository;
import org.booking.config.HikariCPConfig;
import org.booking.entity.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {

    private static final String insert = "INSERT INTO room (room_id, room_price, status) VALUES (?,?,?)";
    @Override
    public void save(Room room) {
        try(Connection connection = HikariCPConfig.getDataSource().getConnection()){
            try(PreparedStatement statement = connection.prepareStatement(insert)){
                statement.setInt(1, room.getRoomId());
                statement.setDouble(2, room.getRoomPrice());
                statement.setString(3, room.getStatus().name());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> findAll() {
        return List.of();
    }
}
