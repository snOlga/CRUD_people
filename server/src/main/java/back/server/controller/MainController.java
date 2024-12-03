package back.server.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import back.server.model.Citizen;
import back.server.model.User;
import back.server.repository.CitizenRepository;
import back.server.repository.UserRepository;
import back.server.security.JwtProvider;
import back.server.util.AmountCitizenException;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.SQLinjectionException;
import back.server.util.UnrealHumanHeightException;
import back.server.validator.NationalityAmountValidator;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private CitizenRepository repoCitizen = new CitizenRepository();
    private JwtProvider jwtProvider = new JwtProvider();
    private NationalityAmountValidator nationalityValidator = new NationalityAmountValidator();

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
            nationalityValidator.validateOneCitizen(citizen);
            repoCitizen.add(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format");
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format");
        } catch (PassportIDUniqueException e) {
            setResponse(response, false, "passport ID is not unique");
        } catch (AmountCitizenException e) {
            setResponse(response, false, "AmountCitizenException");
        } catch (Exception e) {
            setResponse(response, false, "something was wrong");
        }
        return response;
    }

    @PostMapping("/api/send_mass")
    public Map<String, String> sendMassToDB(@RequestBody List<LinkedHashMap<String,String>> jsonArray) {
        Map<String, String> response = defaultResponse();
        try {
            Citizen[] citizens = convertJsonToCitizenArray(jsonArray);
            nationalityValidator.validateArrayOfCitizens(citizens);
            repoCitizen.add(citizens);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format");
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format");
        } catch (PassportIDUniqueException e) {
            setResponse(response, false, "passport ID is not unique");
        } catch (AmountCitizenException e) {
            setResponse(response, false, "AmountCitizenException");
        } catch (Exception e) {
            setResponse(response, false, "something was wrong");
        }
        return response;
    }

    @PostMapping("/api/update_one")
    public Map<String, String> updateOne(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();
        try {
            Citizen citizen = (Citizen) repoCitizen.find(Long.parseLong(json.get("id")));
            if (!userOwnCitizen(citizen, json.get("token")) && !jwtProvider.isAdmin(json.get("token")))
                return response;
            citizen.updateFormJson(json);
            nationalityValidator.validateOneCitizen(citizen);
            repoCitizen.update(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format");
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format");
        } catch (PassportIDUniqueException e) {
            setResponse(response, false, "passport ID is not unique");
        } catch (AmountCitizenException e) {
            setResponse(response, false, "AmountCitizenException");
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
            if (!userOwnCitizen(citizen, json.get("token")) && !jwtProvider.isAdmin(json.get("token")))
                return response;

            repoCitizen.delete(citizen);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (Exception e) {
            setResponse(response, false, "something was wrong");
        }
        return response;
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String message) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("message", message);
    }

    private Map<String, String> defaultResponse() {
        Map<String, String> response = new TreeMap<>();
        setResponse(response, false, "");
        return response;
    }

    private boolean userOwnCitizen(Citizen citizen, String token) {
        UserRepository repoUser = new UserRepository();
        String login = jwtProvider.getUsernameFromJWT(token);
        User user = repoUser.findByLogin(login);

        return citizen.getOwnerNickname().equals(user.getNickname());
    }

    private Citizen[] convertJsonToCitizenArray(List<LinkedHashMap<String,String>> json) throws NumberFormatException, ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
        Citizen[] citizens = new Citizen[json.size()];
        for (int i = 0; i < json.size(); i++) {
            citizens[i] = new Citizen(json.get(i));
        }
        return citizens;
    }
}
