package back.server.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import back.server.DTO.CitizenDTO;
import back.server.model.Citizen;
import back.server.service.CitizenService;
import back.server.util.AmountCitizenException;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.SQLinjectionException;
import back.server.util.UnrealHumanHeightException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private CitizenService citizenService;
    @Autowired
    private MinioClient minioClient;
    @Value("${minio.bucket-name}")
    private String bucketName;

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
        String newFilename = "";
        try {
            newFilename = saveFile(file);
        } catch (IOException e) {
            setResponse(response, false, "something was wrong");
        }
        try {
            String content = new String(file.getBytes());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Map<String, String>>>() {
            }.getType();
            List<Map<String, String>> jsonArray = gson.fromJson(content, listType);
            CitizenDTO[] citizens = convertJsonToCitizenArray(jsonArray);
            citizenService.saveAll(citizens);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "", newFilename);
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format", newFilename);
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format", newFilename);
        } catch (PassportIDUniqueException e) {
            setResponse(response, false, "passport ID is not unique", newFilename);
        } catch (AmountCitizenException e) {
            setResponse(response, false, "AmountCitizenException", newFilename);
        } catch (Exception e) {
            setResponse(response, false, "something was wrong", newFilename);
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
        String newFilename = "";
        try {
            newFilename = saveFile(file);
        } catch (IOException e) {
            setResponse(response, false, "something was wrong");
        }
        try {
            String content = new String(file.getBytes());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Map<String, String>>>() {
            }.getType();
            List<Map<String, String>> jsonArray = gson.fromJson(content, listType);
            CitizenDTO[] citizens = convertJsonToCitizenArray(jsonArray);
            citizenService.deleteAll(citizens);
            messagingTemplate.convertAndSend("/topic/citizen", this.getAll(null));
            setResponse(response, true, "", newFilename);
        } catch (NumberFormatException | UnrealHumanHeightException e) {
            setResponse(response, false, "numbers have wrong format", newFilename);
        } catch (ColorFormatException e) {
            setResponse(response, false, "colors have wrong format", newFilename);
        } catch (PassportIDUniqueException e) {
            setResponse(response, false, "passport ID is not unique", newFilename);
        } catch (AmountCitizenException e) {
            setResponse(response, false, "AmountCitizenException", newFilename);
        } catch (Exception e) {
            setResponse(response, false, "something was wrong", newFilename);
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

    private CitizenDTO[] convertJsonToCitizenArray(List<Map<String, String>> json) throws NumberFormatException,
            ColorFormatException, UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException {
        CitizenDTO[] citizens = new CitizenDTO[json.size()];
        for (int i = 0; i < json.size(); i++) {
            citizens[i] = new CitizenDTO(json.get(i));
        }
        return citizens;
    }

    private long filenameID = 0;

    private String saveFile(MultipartFile file) throws IOException {
        String objectName = filenameID++ + "_" +file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, -1, 10485760).build());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return objectName;
    }
}
