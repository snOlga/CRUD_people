package back.server.security;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.server.repository.UserRepository;
import back.server.users.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserRepository repoUser = new UserRepository();
    private JwtProvider jwtProvider = new JwtProvider();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign_up")
    public Map<String, String> signUp(@RequestBody Map<String, String> json) {
        Map<String, String> response = new TreeMap<>();

        UserDetails user = new User(json.get("nickname"), json.get("login"),
                passwordEncoder.encode(json.get("password")));
        repoUser.add(user);

        SecurityUser securityUser = new SecurityUser(user.getUsername(), user.getPassword(), "USER");

        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        setResponse(response, true, token);

        return response;
    }

    @PostMapping("/log_in")
    public Map<String, String> logIn(@RequestBody Map<String, String> json) {
        Map<String, String> response = new TreeMap<>();

        setResponse(response, false, "");

        if (validateUser(json.get("login"), json.get("login"))) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(json.get("login"), null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            setResponse(response, true, token);
        }

        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String message) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("token", message);
    }

    private boolean validateUser(String login, String password) {

        User user = (User) repoUser.find(login);

        if (user == null)
            return false;

        if (!user.getPassword().equals(passwordEncoder.encode(password)))
            return false;

        return true;
    }
}
