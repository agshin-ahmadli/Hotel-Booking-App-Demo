package org.booking.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private final Properties properties = new Properties();

    public ConfigUtil(){
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getSerializedFilePath(){
        return properties.getProperty("serializedFilePath");
    }

    public boolean isHotelStatusChangeAllowed(){
        return Boolean.parseBoolean(properties.getProperty("hotel.allowChangeStatus"));
    }

}
