package back.server;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import back.server.citizens.Citizen;
import back.server.citizens.exceptions.ColorFormatException;
import back.server.citizens.exceptions.PassportIDUniqueException;
import back.server.citizens.exceptions.UnrealHumanHeightException;
import back.server.repository.CitizenRepository;
import back.server.repository.UserRepository;
import back.server.security.JwtProvider;
import back.server.users.User;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private CitizenRepository repoCitizen = new CitizenRepository();

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MainController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/api/get_all")
    public List<Citizen> getAll(HttpServletRequest request) throws ColorFormatException {
        ArrayList<Citizen> responseList = (ArrayList<Citizen>) repoCitizen.getAll();

        return responseList;
    }

    @PostMapping("/api/send_one")
    public Map<String, String> sendToDB(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();
        try {
            Citizen citizen = new Citizen(json);
            repoCitizen.add(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format");
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format");
        } catch (PassportIDUniqueException e) {
            setResponse(response, false, "passport ID is not unique");
        } catch (Exception e) {
            setResponse(response, false, "something was wrong");
        }
        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String message) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("message", message);
    }

    @PostMapping("/api/update_one")
    public Map<String, String> updateOne(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();
        try {
            Citizen citizen = (Citizen) repoCitizen.find(Long.parseLong(json.get("id")));
            if (!userOwnCitizen(citizen, json.get("token")))
                return response;

            citizen.updateFormJson(json);
            repoCitizen.update(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format");
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format");
        } catch (PassportIDUniqueException e) {
            setResponse(response, false, "passport ID is not unique");
        } catch (Exception e) {
            setResponse(response, false, "something was wrong");
        }
        return response;
    }

    @PostMapping("/api/delete_one")
    public Map<String, String> deleteOne(@RequestBody Map<String, String> json)
            throws MessagingException, ColorFormatException {
        Map<String, String> response = defaultResponse();
        try {
            Citizen citizen = (Citizen) repoCitizen.find(Long.parseLong(json.get("id")));
            if (!userOwnCitizen(citizen, json.get("token")))
                return response;

            repoCitizen.delete(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (Exception e) {
            setResponse(response, false, "something was wrong");
        }
        return response;
    }

    private Map<String, String> defaultResponse() {
        Map<String, String> response = new TreeMap<>();
        setResponse(response, false, "");
        return response;
    }

    private boolean userOwnCitizen(Citizen citizen, String token) {
        UserRepository repoUser = new UserRepository();
        JwtProvider jwtProvider = new JwtProvider();
        String login = jwtProvider.getUsernameFromJWT(token);
        User user = repoUser.findByLogin(login);

        return citizen.getOwnerNickname().equals(user.getNickname());
    }
}
