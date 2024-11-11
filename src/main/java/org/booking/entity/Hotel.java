package org.booking.entity;

import java.util.List;

public class Hotel {
    private String hotelLocation;
    private List<Client> clients;
    private List<Room> rooms;

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
