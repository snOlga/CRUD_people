package back.server.controller;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.server.model.User;
import back.server.service.UserService;
import back.server.util.SQLinjectionException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign_up")
    public Map<String, String> signUp(@RequestBody Map<String, String> json) throws SQLinjectionException {
        Map<String, String> response = defaultResponse();
        User user = new User(json.get("nickname"), json.get("login"),
                json.get("password"));
        String token = userService.registerUser(user);
        setResponse(response, true, token, user.getNickname(), "user");
        return response;
    }

    @PostMapping("/log_in")
    public Map<String, String> logIn(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();

        User user = new User("", json.get("login"),
                json.get("password"));

        String token = userService.loginUser(user);
        setResponse(response, true, token, user.getNickname(), "user");
        return response;
    }

    private Map<String, String> defaultResponse() {
        Map<String, String> response = new TreeMap<>();
        setResponse(response, false, "", "", "user");
        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String token, String nickname,
            String role) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("token", token);
        response.put("nickname", nickname);
        response.put("role", role);
    }
}
