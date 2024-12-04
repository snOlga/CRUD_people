package back.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import back.server.model.Citizen;
import back.server.model.ImportNode;
import back.server.model.User;
import back.server.repository.ImportHistoryRepository;
import back.server.repository.UserRepository;
import back.server.util.ColorFormatException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class ImportHistoryController {

    private ImportHistoryRepository repoImportHistory = new ImportHistoryRepository();
    private UserRepository repoUsers = new UserRepository();

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ImportHistoryController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/history/get_history")
    public List<Citizen> getAll(HttpServletRequest request) throws ColorFormatException {
        ArrayList<Citizen> responseList = (ArrayList<Citizen>) repoImportHistory.getAll();

        return responseList;
    }

    @PostMapping("/history/set_one_history_node")
    public void sendToDB(@RequestBody Map<String, String> json) {
        String fileName = json.get("filename");
        String isSuccessful = json.get("isSuccessful");
        String owner = json.get("owner");
        User user = repoUsers.findByNickname(owner);
        repoImportHistory.add(new ImportNode(fileName, isSuccessful, user));
        messagingTemplate.convertAndSend("/topic/import_history", repoImportHistory.getAll());
    }
}
