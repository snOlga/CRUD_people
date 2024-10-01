package back.server.repository;

import java.util.List;

public interface IRepository {
    public void add(Object object);
    public Object find(long ID);
    public List getAll();
    public void update(Object updatedObject);
    public void delete(Object object);
}
