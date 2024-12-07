package back.server.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import back.server.model.Citizen;
import back.server.repository.CitizenRepository;
import back.server.util.AmountCitizenException;

@Service
public class NationalityAmountValidator {
    private final int MAX_NATIONALITY_MEMBERS = 5;
    // private CitizenRepository repoCitizen;

    
    // public NationalityAmountValidator(CitizenRepository repoCitizen) {
    //     this.repoCitizen = repoCitizen;
    // }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void validateOneCitizen(Citizen currentCitizen) throws AmountCitizenException {
        // List<Citizen> citizens = repoCitizen.findAll();
        // int counter = 0;
        // for (Citizen citizen : citizens) {
        //     counter += citizen.getNationality() == currentCitizen.getNationality() ? 1 : 0;
        // }
        // if (counter >= MAX_NATIONALITY_MEMBERS && counter != 0)
        //     throw new AmountCitizenException("nationality");
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void validateArrayOfCitizens(Citizen[] currentCitizens) throws AmountCitizenException {
        // List<Citizen> citizens = repoCitizen.findAll();
        // int counter = 0;
        // for (Citizen currentCitizen : currentCitizens) {
        //     int currentCounter = 0;
        //     for (Citizen citizen : citizens) {
        //         currentCounter += citizen.getNationality() == currentCitizen.getNationality() ? 1 : 0;
        //     }
        //     for(Citizen citizen : currentCitizens) {
        //         currentCounter += citizen.getNationality() == currentCitizen.getNationality() ? 1 : 0;
        //     }
        //     currentCounter--;
        //     counter = currentCounter > counter ? currentCounter : counter;
        // }
        // if (counter >= MAX_NATIONALITY_MEMBERS && counter != 0)
        //     throw new AmountCitizenException("nationality");
    }

}
