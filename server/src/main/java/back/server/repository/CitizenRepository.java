package back.server.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import back.server.citizens.Citizen;

public class CitizenRepository extends AbstractRepository {

    public CitizenRepository() {
        super();
    }

    @Override
    public void add(Object object) {
        startSession();
        currentSession().persist((Citizen) object);
        closeSession();
    }

    @Override
    public Object find(long ID) {
        startSession();
        Citizen citizen = (Citizen) currentSession().get(Citizen.class, ID);
        closeSession();
        return citizen;
    }

    @Override
    public List readAll() {
        startSession();
        List citizens = currentSession().createQuery("FROM Citizen", Citizen.class).list();
        closeSession();
        return citizens;
    }

    @Override
    public void update(Object updatedObject) {
        startSession();
        currentSession().merge(updatedObject);
        closeSession();
    }

    @Override
    public void delete(Object object) {
        startSession();
        Citizen citizen = (Citizen) currentSession().get(Citizen.class, ((Citizen) object).getId());
        currentSession().remove(citizen);
        closeSession();
    }

}
