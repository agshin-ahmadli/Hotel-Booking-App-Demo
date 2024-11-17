package org.booking.entity;

import java.io.Serial;
import java.io.Serializable;

public class Room  {


    private int roomId;
    private double roomPrice;
    private Status status;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public Room(int roomId, double roomPrice, Status status) {
        this.roomId = roomId;
        this.roomPrice = roomPrice;
        this.status = status;
    }
}
