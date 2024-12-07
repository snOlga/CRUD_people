package back.server.model;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonGetter;

import back.server.enums.Country;
import back.server.repository.CitizenRepository;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.SQLinjectionException;
import back.server.util.UnrealHumanHeightException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "citizens")
public class Citizen extends EntityMetaData {
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String HEIGHT = "height";
    public static final String BIRTHDAY = "birthday";
    public static final String PASSPORT_ID = "passportID";
    public static final String NATIONALITY = "nationality";
    public static final String EYE_COLOR = "eyeColor";
    public static final String HAIR_COLOR = "hairColor";
    public static final String OWNER = "owner";

    @Column(name = NAME)
    private String name;

    @Column(name = GENDER) // 0 for female, other for male
    private byte gender;

    @Column(name = HEIGHT)
    private short height;

    @Column(name = BIRTHDAY)
    private LocalDate birthday;

    @Column(name = PASSPORT_ID, unique = true)
    private Long passportID;

    @Column(name = NATIONALITY)
    @Enumerated(EnumType.STRING)
    private Country nationality;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hexColor", column = @Column(name = EYE_COLOR))
    })
    private HEXColor eyeColor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hexColor", column = @Column(name = HAIR_COLOR))
    })
    private HEXColor hairColor;

    @ManyToOne
    @JoinColumn(name = OWNER, referencedColumnName = "id")
    private User owner;

    public Citizen() {
        setCreationDate(new Date());
        setRegistrationCoordinates(new Coordinates(0, 0));
    }

    public Citizen(String name,
            byte gender,
            String eyeColor,
            String hairColor,
            short height,
            LocalDate birthday,
            long passportID,
            Country nationality) throws ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
        setCreationDate(new Date());
        setRegistrationCoordinates(new Coordinates(0, 0));
        setName(name);
        setGender(gender);
        setHairColor(hairColor);
        setEyeColor(eyeColor);
        setHeight(height);
        setBirthday(birthday);
        setPassportID(passportID);
        setNationality(nationality);
    }

    public Citizen(Map<String, String> json)
            throws ColorFormatException, UnrealHumanHeightException, NumberFormatException, PassportIDUniqueException, SQLinjectionException {
        setCreationDate(new Date());
        setRegistrationCoordinates(new Coordinates(json.get("xCoord"), json.get("yCoord")));
        setName(json.get(NAME));
        setGender(Byte.parseByte(json.get(GENDER)));
        setHairColor(json.get(HAIR_COLOR));
        setEyeColor(json.get(EYE_COLOR));
        setHeight(Short.parseShort(json.get(HEIGHT)));
        setBirthday(LocalDate.parse(json.get(BIRTHDAY)));
        setPassportID(Long.parseLong(json.get(PASSPORT_ID)));
        setNationality(Country.valueOf(json.get(NATIONALITY)));
        // setOwner("json.get("token")"); // TODO:
    }

    public void setName(String name) throws SQLinjectionException {
        this.name = validateStringValue(name);
    }

    public void setGender(byte gender) {
        this.gender = gender;
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

    public void setHeight(String heightStr) throws UnrealHumanHeightException {
        this.setHeight(Short.parseShort(heightStr));
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setPassportID(Long passportID) throws PassportIDUniqueException {
        this.passportID = passportID;
    }

    public void setPassportID(String passportIDStr) throws NumberFormatException, PassportIDUniqueException {
        this.setPassportID(Long.parseLong(passportIDStr));
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    // public void setOwner(String token) {
    //     UserRepository repoUser = new UserRepository();
    //     JwtProvider jwtProvider = new JwtProvider();
    //     String login = jwtProvider.getUsernameFromJWT(token);
    //     User user = repoUser.findByLogin(login);
    //     setOwner(user);
    // }

    public String getName() {
        return name;
    }

    public byte getGender() {
        return gender;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public Long getPassportID() {
        return passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public User getOwner() {
        return owner;
    }

    @JsonGetter(OWNER)
    public String getOwnerNickname() {
        return owner.getNickname();
    }

    public void updateFormJson(Map<String, String> json) throws ColorFormatException, UnrealHumanHeightException, NumberFormatException, PassportIDUniqueException, SQLinjectionException {
        if (!json.get(NAME).isEmpty())
            setName(json.get(NAME));
        if (!json.get(HAIR_COLOR).isEmpty())
            setHairColor(json.get(HAIR_COLOR));
        if (!json.get(HEIGHT).isEmpty())
            setHeight(json.get(HEIGHT));
        if (!json.get(PASSPORT_ID).isEmpty())
            setPassportID(json.get(PASSPORT_ID));
    }

    private String validateStringValue (String line) throws SQLinjectionException {
        if (!line.contains("drop")) {
            return line;
        }
        else {
            throw new SQLinjectionException();
        }
    }
}
