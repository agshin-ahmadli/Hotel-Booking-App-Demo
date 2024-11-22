package org.booking.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rooms")
public class Room  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private double roomPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Hotel hotel;

    @ManyToMany
    private List<Ticket>tickets;




    public Long getId() {
        return Id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setId(Long id) {
        Id = id;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Room() {
        status = Status.AVAILABLE;
    }

    public Room(Long Id, double roomPrice, Status status) {
        this.Id = Id;
        this.roomPrice = roomPrice;
        this.status = status;
    }
}
