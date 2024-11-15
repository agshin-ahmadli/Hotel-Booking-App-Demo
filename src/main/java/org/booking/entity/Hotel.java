package org.booking.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String hotelLocation;
    private List<Client> clients;
    private List<Room> rooms;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Hotel(String hotelLocation, List<Client> clients, List<Room> rooms) {
        this.hotelLocation = hotelLocation;
        this.clients = clients;
        this.rooms = rooms;
    }

    public Hotel() {
    }
}
