package back.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import back.server.model.User;
import back.server.repository.UserRepository;
import back.server.security.JwtProvider;
import back.server.security.SecurityUser;
import back.server.util.SQLinjectionException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    public String registerUser(User user) throws SQLinjectionException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userExists(user.getNickname(), user.getLogin()))
            return "";

        userRepo.save(user);

        SecurityUser securityUser = new SecurityUser(user.getUsername(), user.getPassword(), "USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return token;
    }

    public String loginUser(User user) {
        if (!validateUser(user.getLogin(), user.getPassword()))
            return "";
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLogin(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return token;
    }

    public User findByNickname(String nickname) {
        return userRepo.findByNickname(nickname);
    }

    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    private boolean userExists(String nickname, String login) {
        try {
            userRepo.findByLogin(login);
            userRepo.findByNickname(nickname);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateUser(String login, String password) {
        List<User> users = userRepo.findAllByLogin(login);

        if (users.size() == 0)
            return false;

        for (User itUser : users) {
            if (!passwordEncoder.matches(password, itUser.getPassword()))
                return false;
        }

        return true;
    }
}
