package back.server.repository;

import java.util.List;

import back.server.citizens.Citizen;

public class CitizenRepository extends AbstractRepository {

    public CitizenRepository() {
        super();
    }

    @Override
    public void add(Object object) {
        runQuery(() -> {
            currentSession().persist((Citizen) object);
            return 0;
        });
    }

    @Override
    public Citizen find(long ID) {
        return (Citizen) runQuery(() -> (Citizen) currentSession().get(Citizen.class, ID));
    }

    @Override
    public List getAll() {
        return (List) runQuery(() -> currentSession().createQuery("FROM Citizen", Citizen.class).list());
    }

    @Override
    public void update(Object updatedObject) {
        runQuery(() -> currentSession().merge(updatedObject));
    }

    @Override
    public void delete(Object object) {
        runQuery(() -> {
            Citizen citizen = (Citizen) currentSession().get(Citizen.class, ((Citizen) object).getId());
            currentSession().remove(citizen);
            return 0;
        });
    }

}
