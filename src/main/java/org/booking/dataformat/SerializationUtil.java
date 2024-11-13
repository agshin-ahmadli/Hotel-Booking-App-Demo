package org.booking.dataformat;

import org.booking.entity.Hotel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtil {

    public static void saveState(Hotel hotel, String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(hotel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Hotel loadState(String filePath) {
        Hotel hotel = null;
        try (FileInputStream fileInput = new FileInputStream(filePath);
             ObjectInputStream objInput = new ObjectInputStream(fileInput)) {

            hotel = (Hotel) objInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hotel;
    }
}
