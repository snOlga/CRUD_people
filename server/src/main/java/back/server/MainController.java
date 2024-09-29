package back.server;

import java.util.Date;

import org.springframework.web.bind.annotation.*;

import back.server.citizens.Citizen;
import back.server.citizens.Country;
import back.server.repository.DBmanipulating;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @GetMapping("/api/dosmt")
    public void doSmt(HttpServletRequest request) {
        DBmanipulating repo = new DBmanipulating();
        repo.configure();
        
        
        Citizen citizen = new Citizen("Mike", "black", "blond", (short)180, new Date(), (long)1, Country.RUSSIA);
        repo.addCitizen(citizen);
    }
}
