package back.server.users;

import back.server.EntityMetaData;
import back.server.citizens.Coordinates;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "citizen_users")
public class User extends EntityMetaData implements UserDetails {
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public User() {
        setCreationDate(new Date());
        setRegistrationCoordinates(new Coordinates(0, 0));
    }

    public User(String nickname, String login, String password) {
        this.nickname = nickname;
        this.login = login;
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList list = new ArrayList<>();
        list.add(nickname);
        list.add(login);
        return list;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
