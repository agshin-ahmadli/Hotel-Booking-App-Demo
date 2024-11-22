package org.booking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "clients")
public class Client  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> roomList;

    @ManyToOne
    private Hotel hotel;

    @OneToMany(mappedBy ="client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> bookingTicket = new ArrayList<>();

    public void setClientId(Long  clientId) {
        this.id = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public Long getClientId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Ticket> getBookingTicket() {
        return bookingTicket;
    }

    public void setBookingTicket(List<Ticket> bookingTicket) {
        this.bookingTicket = bookingTicket;
    }

    public Client() {
        this.id = null;
        this.name = "";
        this.roomList = null;
    }

    public Client(Long clientId, String name, List<Room> roomList) {
        this.id = clientId;
        this.name = name;
        this.roomList = roomList;
    }

    public Client(List<Room> roomList) {
        this.roomList = roomList;
    }
}
