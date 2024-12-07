package back.server.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import back.server.model.Citizen;
import back.server.model.User;
import back.server.repository.CitizenRepository;
import back.server.security.JwtProvider;
import back.server.util.AmountCitizenException;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.SQLinjectionException;
import back.server.util.UnrealHumanHeightException;
import back.server.validator.NationalityAmountValidator;

@Service
public class CitizenService {
    @Autowired
    private CitizenRepository citizenRepo;
    @Autowired
    private NationalityAmountValidator nationalityValidator;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    public List<Citizen> findAll() {
        return citizenRepo.findAll();
    }

    public void save(Citizen citizen) throws AmountCitizenException {
        nationalityValidator.validateOneCitizen(citizen);
        citizenRepo.save(citizen);
    }

    public void saveAll(Citizen[] citizens) throws AmountCitizenException {
        nationalityValidator.validateArrayOfCitizens(citizens);
        citizenRepo.saveAll(Arrays.asList(citizens));
    }

    public boolean updateFromJson(Map<String, String> json) throws NumberFormatException, ColorFormatException,
            UnrealHumanHeightException, PassportIDUniqueException, SQLinjectionException, AmountCitizenException {
        Citizen citizen = (Citizen) citizenRepo.findById(Long.parseLong(json.get("id"))).get();
        if (!userOwnCitizen(citizen, json.get("token")) && !jwtProvider.isAdmin(json.get("token")))
            return false;
        citizen.updateFormJson(json);
        nationalityValidator.validateOneCitizen(citizen);
        citizenRepo.save(citizen);
        return true;
    }

    public boolean deleteFromJson(Map<String, String> json) {
        Citizen citizen = (Citizen) citizenRepo.findById(Long.parseLong(json.get("id"))).get();
        if (!userOwnCitizen(citizen, json.get("token")) && !jwtProvider.isAdmin(json.get("token")))
            return false;

            citizenRepo.delete(citizen);
            return true;
    }

    public Citizen findByPassportID(Long passportID) {
        return citizenRepo.findByPassportID(passportID);
    }

    private boolean userOwnCitizen(Citizen citizen, String token) {
        String login = jwtProvider.getUsernameFromJWT(token);
        User user = userService.findByLogin(login);

        return citizen.getOwnerNickname().equals(user.getNickname());
    }
}
