package back.server;

import java.util.Date;

import org.springframework.web.bind.annotation.*;

import back.server.citizens.Citizen;
import back.server.citizens.ColorFormatException;
import back.server.citizens.Country;
import back.server.repository.CitizenRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @GetMapping("/api/dosmt")
    public void doSmt(HttpServletRequest request) throws ColorFormatException{
        CitizenRepository repo = new CitizenRepository();
        
        Citizen citizen = new Citizen("David", "#000000", "#000000", (short)180, new Date(), (long)1, Country.RUSSIA);
        repo.add(citizen);
    }
}
