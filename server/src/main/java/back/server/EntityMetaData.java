package back.server;

import back.server.citizens.Coordinates;
import jakarta.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class EntityMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registrationCoordinates", referencedColumnName = "id")
    private Coordinates registrationCoordinates;

    @Column(name = "creationDate")
    private Date creationDate;

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegistrationCoordinates(Coordinates registrationCoordinates) {
        this.registrationCoordinates = registrationCoordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public Coordinates getRegistrationCoordinates() {
        return registrationCoordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
