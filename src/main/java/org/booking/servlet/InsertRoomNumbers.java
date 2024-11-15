package org.booking.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/insertRoom")
public class InsertRoomNumbers extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String num1Str = req.getParameter("num1");
        String num2Str = req.getParameter("num2");

        int sum = 0;
        try{
            int num1 = Integer.parseInt(num1Str);
            int num2 = Integer.parseInt(num2Str);
            sum = num1 + num2;
        }
        catch (NumberFormatException e){
            return;
        }

        resp.setContentType("text/plain");
        resp.getWriter().println(sum);
    }

}