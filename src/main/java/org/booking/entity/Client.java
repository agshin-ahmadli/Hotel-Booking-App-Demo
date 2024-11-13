package org.booking.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer clientId;
    private String name;
    private List<Room> roomList = new ArrayList<>();

    public void setClientId(int  clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public Client() {
        this.clientId = null;
        this.name = "";
        this.roomList = null;
    }

    public Client(Integer clientId, String name, List<Room> roomList) {
        this.clientId = clientId;
        this.name = name;
        this.roomList = roomList;
    }

    public Client(List<Room> roomList) {
        this.roomList = roomList;
    }
}
