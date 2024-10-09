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
import back.server.citizens.exceptions.UnrealHumanHeightException;
import back.server.repository.CitizenRepository;
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

    // isSuccessful : yes/no
    // message :
    @PostMapping("/api/send_one")
    public Map<String, String> sendToDB(@RequestBody Map<String, String> json) {
        Map<String, String> response = new TreeMap<>();
        try {
            Citizen citizen = new Citizen(json);
            repoCitizen.add(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format");
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format");
        }
        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String message) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("message", message);
    }

    @PostMapping("/api/update_one")
    public Map<String, String> updateOne(@RequestBody Map<String, String> json) {
        Citizen citizen = (Citizen) repoCitizen.find(Long.parseLong(json.get("id")));
        Map<String, String> response = new TreeMap<>();
        try {
            citizen.updateFormJson(json);
            repoCitizen.update(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format");
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format");
        }
        return response;
    }

    @PostMapping("/api/delete_one")
    public void deleteOne(@RequestBody Map<String, String> json) throws MessagingException, ColorFormatException {
        Citizen citizen = (Citizen) repoCitizen.find(Long.parseLong(json.get("id")));
        repoCitizen.delete(citizen);
        messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
    }
}
