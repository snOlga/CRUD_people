package back.server;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.util.TypeKey;

import back.server.citizens.Citizen;
import back.server.citizens.exceptions.ColorFormatException;
import back.server.citizens.exceptions.UnrealHumanHeightException;
import back.server.repository.CitizenRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private CitizenRepository repoCitizen = new CitizenRepository();

    @GetMapping("/api/get_all")
    public List<Citizen> getAll(HttpServletRequest request) throws ColorFormatException {
        ArrayList<Citizen> responseList = (ArrayList<Citizen>) repoCitizen.getAll();

        return responseList;
    }

    // isSuccessful : yes/no
    // message :
    @PostMapping("/api/send_one")
    public Map<String, String> sendToDB(@RequestBody Map<String, String> json) {
        System.out.println("-----------------------------------------------");
        for (String name : json.keySet()) {
            String value = json.get(name);
            System.out.println(name + " " + value);
        }
        System.out.println("-----------------------------------------------");
        Map<String, String> response = new TreeMap<>();
        try {
            Citizen citizen = new Citizen(json);
            repoCitizen.add(citizen);
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
}
