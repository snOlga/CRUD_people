package back.server.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import back.server.DTO.CitizenDTO;
import back.server.model.Citizen;
import back.server.service.CitizenService;
import back.server.service.FileCitizenService;
import back.server.util.AmountCitizenException;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.UnrealHumanHeightException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private CitizenService citizenService;
    @Autowired
    private FileCitizenService fileCitizenService;

    private final SimpMessagingTemplate messagingTemplate;

    public MainController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/api/get_all")
    public List<Citizen> getAll(HttpServletRequest request) throws ColorFormatException {
        return citizenService.findAll();
    }

    @PostMapping("/api/send_one")
    public Map<String, String> sendToDB(@RequestBody Map<String, String> json) {
        Map<String, String> response = defaultResponse();
        try {
            CitizenDTO citizen = new CitizenDTO(json);
            citizenService.save(citizen);
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
    public Map<String, String> sendMassToDB(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = defaultResponse();
        try {
            String newFilename = fileCitizenService.importFromFile(file);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "", newFilename);
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
            citizenService.updateFromJson(json);
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
            citizenService.deleteFromJson(json);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "");
        } catch (Exception e) {
            setResponse(response, false, "something was wrong");
        }
        return response;
    }

    @PostMapping("/api/delete_mass")
    public Map<String, String> deleteMass(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = defaultResponse();
        try {
            String newFilename = fileCitizenService.deleteFromFile(file);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "", newFilename);
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

    private void setResponse(Map<String, String> response, boolean isSuccessful, String message) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("message", message);
    }

    private void setResponse(Map<String, String> response, boolean isSuccessful, String message, String filename) {
        response.put("isSuccessful", isSuccessful + "");
        response.put("message", message);
        response.put("filename", filename);
    }

    private Map<String, String> defaultResponse() {
        Map<String, String> response = new TreeMap<>();
        setResponse(response, false, "");
        return response;
    }
}
