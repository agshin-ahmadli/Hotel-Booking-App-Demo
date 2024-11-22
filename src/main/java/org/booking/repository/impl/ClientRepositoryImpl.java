package org.booking.repository.impl;
import jakarta.persistence.EntityManager;
import org.booking.models.Client;
import org.booking.repository.ClientRepository;
import org.booking.util.HibernateUtil;
import org.hibernate.Session;
import java.util.*;

public class ClientRepositoryImpl implements ClientRepository {


    @Override
    public void saveClient(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
       try {
           session.beginTransaction();
           session.persist(client);
           session.getTransaction().commit();
       } finally {
        session.close();
        }
    }


    @Override
    public List<Client> clientList(){
        EntityManager em = HibernateUtil.getSessionFactory().openSession();
        List<Client> clients = em.createQuery("from Client", Client.class).getResultList();
        em.close();
        return clients;
    }


    public Optional<Client> findById(Long clientId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Client client = session.get(Client.class, clientId);
        return Optional.ofNullable(client);
    }
}
