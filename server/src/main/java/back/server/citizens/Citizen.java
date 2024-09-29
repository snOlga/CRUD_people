package back.server.citizens;


import jakarta.persistence.*;

@Entity
@Table (name = "citizens")
public class Citizen {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;  

    @Column
    private java.util.Date creationDate; 

    @Column
    private String eyeColor; 

    @Column
    private String hairColor;

    @Column
    private Long height; 

    @Column
    private java.util.Date birthday;

    @Column
    private Long passportID; 

    @Column
    @ManyToMany
    @JoinColumn(name = "registrationCoordinates", referencedColumnName="id")
    private Coordinates registrationCoordinates;

    @Enumerated(EnumType.STRING)
    private Country nationality;

    public Long getId() {
        return id;
    }

    
}
