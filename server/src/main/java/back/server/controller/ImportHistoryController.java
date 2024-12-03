package back.server.controller;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import back.server.model.ImportNode;
import back.server.repository.ImportHistoryRepository;

@RestController
@CrossOrigin(origins = "*")
public class ImportHistoryController {

    private ImportHistoryRepository repoImportHistory = new ImportHistoryRepository();

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ImportHistoryController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/history/set_one_history_node")
    public void sendToDB(@RequestBody Map<String, String> json) {
        String fileName = json.get("filename");
        String isSuccessful = json.get("isSuccessful");
        repoImportHistory.add(new ImportNode(fileName, isSuccessful));
        messagingTemplate.convertAndSend("/topic/import_history", repoImportHistory.getAll());
    }
}
