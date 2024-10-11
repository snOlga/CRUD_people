package back.server.repository;

import java.util.List;

import back.server.users.User;
import jakarta.persistence.Query;

public class UserRepository extends AbstractRepository {

    public UserRepository() {
        super();
    }

    @Override
    public void add(Object object) {
        runQuery(() -> {
            currentSession().persist((User) object);
            return 0;
        });
    }

    @Override
    public User find(long ID) {
        return (User) runQuery(() -> (User) currentSession().get(User.class, ID));
    }

    public User find(String currentLogin) {
        return (User) runQuery(() -> {
            Query query = currentSession().createQuery("FROM User where login = :currentLogin");
            query.setParameter("currentLogin", currentLogin);
            return (User) query.getSingleResult();
        });
    }

    @Override
    public List getAll() {
        return (List) runQuery(() -> currentSession().createQuery("FROM User", User.class).list());
    }

    @Override
    public void update(Object updatedObject) {
        runQuery(() -> currentSession().merge(updatedObject));
    }

    @Override
    public void delete(Object object) {
        runQuery(() -> {
            User user = (User) currentSession().get(User.class, ((User) object).getId());
            currentSession().remove(user);
            return 0;
        });
    }

}
