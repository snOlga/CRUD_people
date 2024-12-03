package back.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "import_history")
public class ImportNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "is_successful")
    private String isSuccessful;
    
    public ImportNode() {}

    public ImportNode(String fileName, String isSuccessful) {
        this.fileName = fileName;
        this.isSuccessful = isSuccessful;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIsSuccessful(String isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getFileName() {
        return fileName;
    }

    public String getIsSuccessful() {
        return isSuccessful;
    }

    public Integer getId() {
        return id;
    }
}
