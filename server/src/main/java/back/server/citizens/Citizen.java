package back.server.citizens;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "eyeColor")
    private String eyeColor;

    @Column(name = "hairColor")
    private String hairColor;

    @Column(name = "height")
    private short height;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "passportID")
    private Long passportID;

    @Column(name = "nationality")
    @Enumerated(EnumType.STRING)
    private Country nationality;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registrationCoordinates", referencedColumnName = "id")
    private Coordinates registrationCoordinates;

    public Citizen() {
        creationDate = new Date();
    }

    public Citizen(String name,
            String eyeColor,
            String hairColor,
            short height,
            Date birthday,
            long passportID,
            Country nationality) {
        creationDate = new Date();
        registrationCoordinates = new Coordinates(0, 0);
        this.name = name;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.nationality = nationality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(java.util.Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    public void setPassportID(Long passportID) {
        this.passportID = passportID;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public java.util.Date getBirthday() {
        return birthday;
    }

    public Long getPassportID() {
        return passportID;
    }
}
