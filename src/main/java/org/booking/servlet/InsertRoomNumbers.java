package org.booking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.booking.entity.Room;
import org.booking.entity.Status;
import org.booking.repository.RoomRepository;
import org.booking.repository.RoomRepositoryImpl;
import java.io.*;


@WebServlet("/InsertRoom")

public class InsertRoomNumbers extends HttpServlet {

    RoomRepository repository = new RoomRepositoryImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String roomNum = request.getParameter("roomId");
        String price = request.getParameter("roomPrice");
        String status = request.getParameter("roomStatus");


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            if (roomNum == null || price == null || status == null) {
                throw new ServletException("All parameters are required");
            }


            int roomNumber = Integer.parseInt(roomNum);
            double roomPrice = Double.parseDouble(price);
            Status roomStatus = Status.valueOf(status);


            Room room = new Room(roomNumber, roomPrice, roomStatus);
            repository.save(room);



            out.println("<html><body>");
            out.println("<h1>Room Insertion Success!</h1>");
            out.println("<p>Room ID: " + roomNumber + "</p>");
            out.println("<p>Room Price: " + roomPrice + "</p>");
            out.println("<p>Room Status: " + roomStatus + "</p>");
            out.println("</body></html>");

        } catch (Exception e) {

            out.println("<html><body>");
            out.println("<h1>Error: Unable to Insert Room</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<a href='roomForm.html'>Go Back</a>");
            out.println("</body></html>");
        }
    }


}
