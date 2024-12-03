package back.server.repository;

import java.util.List;

import back.server.model.Citizen;
import back.server.model.ImportNode;
import jakarta.persistence.Query;

public class ImportHistoryRepository extends AbstractRepository<ImportNode> {

    public ImportHistoryRepository() {
        super();
    }

    @Override
    public void add(ImportNode object) {
        runQuery(() -> {
            currentSession().persist(object);
            return 0;
        });
    }

    @Override
    public ImportNode find(long ID) {
        return (ImportNode) runQuery(() -> (ImportNode) currentSession().get(ImportNode.class, ID));
    }

    @Override
    public List getAll() {
        return (List) runQuery(() -> currentSession().createQuery("FROM ImportNode", ImportNode.class).list());
    }

    @Override
    public void update(ImportNode updatedObject) {
        runQuery(() -> currentSession().merge(updatedObject));
    }

    @Override
    public void delete(ImportNode object) {
        runQuery(() -> {
            ImportNode node = (ImportNode) currentSession().get(ImportNode.class, (object).getId());
            currentSession().remove(node);
            return 0;
        });
    }
}
