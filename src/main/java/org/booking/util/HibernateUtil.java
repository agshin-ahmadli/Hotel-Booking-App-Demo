package org.booking.util;

import org.booking.models.Client;
import org.booking.models.Hotel;
import org.booking.models.Room;
import org.booking.models.Ticket;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory SESSION_FACTORY;

    static {
        try{
            SESSION_FACTORY = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Room.class)
                    .addAnnotatedClass(Hotel.class)
                    .addAnnotatedClass(Ticket.class)
                    .buildSessionFactory();
        } catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed." + ex);
        }
    }


    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }


    public void shutdown() {
        if (SESSION_FACTORY != null) {
            SESSION_FACTORY.close();
        }
    }
}
