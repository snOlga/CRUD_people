package back.server.repository;

import java.util.List;

import back.server.citizens.Citizen;

public class CitizenRepository extends AbstractRepository<Citizen> {

    public CitizenRepository() {
        super();
    }

    @Override
    public void add(Citizen object) {
        runQuery(() -> {
            currentSession().persist(object);
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
    public void update(Citizen updatedObject) {
        runQuery(() -> currentSession().merge(updatedObject));
    }

    @Override
    public void delete(Citizen object) {
        runQuery(() -> {
            Citizen citizen = (Citizen) currentSession().get(Citizen.class, (object).getId());
            currentSession().remove(citizen);
            return 0;
        });
    }

}
