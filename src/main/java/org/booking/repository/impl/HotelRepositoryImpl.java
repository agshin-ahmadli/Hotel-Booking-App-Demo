package org.booking.repository.impl;

import org.booking.models.Hotel;
import org.booking.repository.HotelRepository;
import org.booking.util.HibernateUtil;
import org.hibernate.Session;

public class HotelRepositoryImpl implements HotelRepository {
    @Override
    public void addHotel(Hotel hotel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.persist(hotel);
            session.getTransaction().commit();
        }finally{
            session.close();
        }
    }
}
