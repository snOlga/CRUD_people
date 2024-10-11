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
        startSession();
        currentSession().persist((User) object);
        closeSession();
    }

    @Override
    public Object find(long ID) {
        startSession();
        User user = (User) currentSession().get(User.class, ID);
        closeSession();
        return user;
    }

    public Object find(String currentLogin) {
        startSession();
        Query query = currentSession().createQuery("FROM User where login = :currentLogin");
        query.setParameter("currentLogin", currentLogin);
        User user = (User) query.getSingleResult();
        closeSession();
        return user;
    }

    @Override
    public List getAll() {
        startSession();
        List users = currentSession().createQuery("FROM User", User.class).list();
        closeSession();
        return users;
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
        User user = (User) currentSession().get(User.class, ((User) object).getId());
        currentSession().remove(user);
        closeSession();
    }

}
