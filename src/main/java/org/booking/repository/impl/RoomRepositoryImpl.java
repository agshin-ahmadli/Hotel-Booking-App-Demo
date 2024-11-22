package org.booking.repository.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.booking.models.Client;
import org.booking.models.Room;
import org.booking.models.Status;
import org.booking.repository.RoomRepository;
import org.booking.util.HibernateUtil;
import org.booking.util.JpaUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class RoomRepositoryImpl implements RoomRepository {


    public void save(Room room) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

                session.merge(room); // Existing entity


            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
    @Override
    public List<Room> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Room> rooms = session.createQuery("from Room", Room.class).getResultList();
        session.close();
        return rooms;
    }

    @Override
    public List<Room> getRoomsWithPagination(int pageNumber, int pageSize) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = cb.createQuery(Room.class);
        Root<Room> root = criteriaQuery.from(Room.class);

        criteriaQuery.select(root)
                .where(cb.equal(root.get("status"), Status.AVAILABLE));
        TypedQuery<Room> tq = em.createQuery(criteriaQuery);
        tq.setFirstResult((pageNumber - 1) * pageSize);
        tq.setMaxResults(pageSize);
        return tq.getResultList();
    }




    @Override
    public void deleteById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try{
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            if(room != null){
                em.remove(room);
            }
            em.getTransaction().commit();
        }finally{
                em.close();
        }

    }

    @Override
    public Optional<Room> findAvailableRoomById(long id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Room room = em.find(Room.class, id);
        return Optional.ofNullable(room)
                .filter(r -> r.getStatus() == Status.AVAILABLE);
    }

    @Override
    public void updateRoom(Room room) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(room);
            transaction.commit();

        } finally {
            session.close();

        }
    }
}
