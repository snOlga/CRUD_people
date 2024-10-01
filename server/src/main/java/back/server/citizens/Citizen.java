package back.server.citizens;

import java.util.Date;
import java.util.Map;

import back.server.EntityMetaData;
import back.server.citizens.exceptions.ColorFormatException;
import back.server.citizens.exceptions.UnrealHumanHeightException;
import jakarta.persistence.*;

@Entity
@Table(name = "citizens")
public class Citizen extends EntityMetaData {
    @Column(name = "name")
    private String name;

    @Column(name = "height")
    private short height;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "passportID")
    private Long passportID;

    @Column(name = "nationality")
    @Enumerated(EnumType.STRING)
    private Country nationality;

    @Embedded
    @AttributeOverrides({ 
        @AttributeOverride(name = "hexColor", column = @Column(name = "eyeColor")) 
        }) 
    private HEXColor eyeColor;

    @Embedded
    @AttributeOverrides({ 
        @AttributeOverride(name = "hexColor", column = @Column(name = "hairColor")) 
        }) 
    private HEXColor hairColor;

    public Citizen() {
        setCreationDate(new Date());
        setRegistrationCoordinates(new Coordinates(0, 0));
    }

    public Citizen(String name,
            String eyeColor,
            String hairColor,
            short height,
            Date birthday,
            long passportID,
            Country nationality) throws ColorFormatException, UnrealHumanHeightException {
        setCreationDate(new Date());
        setRegistrationCoordinates(new Coordinates(0, 0));
        setName(name);
        setHairColor(hairColor);
        setEyeColor(eyeColor);
        setHeight(height);
        setBirthday(birthday);
        setPassportID(passportID);
        setNationality(nationality);
    }

    public Citizen(Map<String, String> json) throws ColorFormatException, UnrealHumanHeightException, NumberFormatException{
        setCreationDate(new Date());
        setRegistrationCoordinates(new Coordinates(json.get("xCoord"), json.get("yCoord")));
        setName(json.get("name"));
        setHairColor(json.get("hairColor"));
        setEyeColor(json.get("eyeColor"));
        setHeight(Short.parseShort(json.get("height")));
        setBirthday(new Date(json.get("birthday")));
        setPassportID(Long.parseLong(json.get("passportID")));
        setNationality(Country.valueOf(json.get("nationality")));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEyeColor(String eyeColor) throws ColorFormatException {
        this.eyeColor = new HEXColor(eyeColor);
    }

    public void setHairColor(String hairColor) throws ColorFormatException {
        this.hairColor = new HEXColor(hairColor);
    }

    public void setHeight(short height) throws UnrealHumanHeightException {
        if (height > 250)
            throw new UnrealHumanHeightException(height + "");
        this.height = height;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    public void setPassportID(Long passportID) {
        this.passportID = passportID;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public String getEyeColor() {
        return eyeColor.toString();
    }

    public String getHairColor() {
        return hairColor.toString();
    }

    public short getHeight() {
        return height;
    }

    public java.util.Date getBirthday() {
        return birthday;
    }

    public Long getPassportID() {
        return passportID;
    }

    public Country getNationality() {
        return nationality;
    }
}
