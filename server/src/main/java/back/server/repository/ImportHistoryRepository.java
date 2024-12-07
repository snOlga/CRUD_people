package back.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import back.server.model.ImportNode;

@Repository
public interface ImportHistoryRepository extends JpaRepository<ImportNode, Long> {
    
}
