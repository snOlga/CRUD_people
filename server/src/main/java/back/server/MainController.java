package back.server;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

import back.server.citizens.Citizen;
import back.server.citizens.ColorFormatException;
import back.server.repository.CitizenRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @GetMapping("/api/get_all")
    public List<Citizen> getAll(HttpServletRequest request) throws ColorFormatException{
        CitizenRepository repoCitizen = new CitizenRepository();
        ArrayList<Citizen> responseList = (ArrayList<Citizen>) repoCitizen.getAll();

        return responseList;
    }
}
