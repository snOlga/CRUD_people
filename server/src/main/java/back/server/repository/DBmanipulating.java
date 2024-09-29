package back.server.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import back.server.citizens.Citizen;

import java.util.List;

public class DBmanipulating {
    private static SessionFactory sessionFactory;

    public DBmanipulating() {
    }

    public void configure() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void addCitizen(Citizen citizen) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        session.persist(citizen);
        transaction.commit();
        session.close();
    }

    public List<Citizen> getAllCitizens() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List citizens = session.createQuery("FROM Citizen", Citizen.class).list();

        transaction.commit();
        session.close();
        return citizens;
    }

    public void updateCitizen(Citizen updatedCitizen) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        session.merge(updatedCitizen);
        transaction.commit();
        session.close();
    }

    public void removeCitizen(Citizen citizenToDelete) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Citizen citizen = (Citizen) session.get(Citizen.class, citizenToDelete.getId());
        session.remove(citizen);
        transaction.commit();
        session.close();
    }

}
