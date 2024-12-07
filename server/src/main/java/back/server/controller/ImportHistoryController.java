package back.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import back.server.model.ImportNode;
import back.server.model.User;
import back.server.repository.ImportHistoryRepository;
import back.server.service.UserService;
import back.server.util.ColorFormatException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class ImportHistoryController {

    @Autowired
    private UserService userService;

    private ImportHistoryRepository repoImportHistory;
    private SimpMessagingTemplate messagingTemplate;

    public ImportHistoryController(SimpMessagingTemplate messagingTemplate, ImportHistoryRepository repoImportHistory) {
        this.messagingTemplate = messagingTemplate;
        this.repoImportHistory = repoImportHistory;
    }

    @GetMapping("/history/get_history")
    public List<ImportNode> getAll(HttpServletRequest request) throws ColorFormatException {
        ArrayList<ImportNode> responseList = (ArrayList<ImportNode>) repoImportHistory.findAll();

        return responseList;
    }

    @PostMapping("/history/set_one_history_node")
    public void sendToDB(@RequestBody Map<String, String> json) {
        String fileName = json.get("filename");
        String isSuccessful = json.get("isSuccessful");
        String owner = json.get("owner");
        User user = userService.findByNickname(owner);
        repoImportHistory.save(new ImportNode(fileName, isSuccessful, user));
        messagingTemplate.convertAndSend("/topic/import_history", repoImportHistory.findAll());
    }
}
