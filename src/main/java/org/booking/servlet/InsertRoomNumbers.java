package org.booking.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.booking.models.Room;
import org.booking.repository.RoomRepository;
import org.booking.repository.impl.RoomRepositoryImpl;
import java.io.*;


@WebServlet("/add-room")
public class InsertRoomNumbers extends HttpServlet {

    RoomRepository repository = new RoomRepositoryImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {


        StringBuilder json = new StringBuilder();
        try(BufferedReader reader = request.getReader()) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                json.append(inputLine);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Room room = objectMapper.readValue(json.toString(), Room.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }

       try {
           response.setContentType("application/json");
           response.getWriter().write("{\"message\": \"Room added successfully\"}");
       }catch (IOException e){
           e.printStackTrace();
       }

    }
}
