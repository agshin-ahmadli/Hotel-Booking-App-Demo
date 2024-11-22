package org.booking.repository.impl;

import org.booking.models.Client;
import org.booking.models.Ticket;
import org.booking.repository.TicketRepository;
import org.booking.util.HibernateUtil;
import org.hibernate.Session;

public class TicketRepositoryImpl implements TicketRepository {
    @Override
    public void addBookingTicket(Ticket ticket) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Client client = ticket.getClient();
            if (client != null && session.get(Client.class, client.getClientId()) == null) {
                session.persist(client);
            }
            session.persist(ticket);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }



}
