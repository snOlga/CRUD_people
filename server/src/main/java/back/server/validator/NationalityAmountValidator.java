package back.server.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import back.server.model.Citizen;
import back.server.repository.CitizenRepository;


public class NationalityAmountValidator {
    private final int MAX_NATIONALITY_MEMBERS = 5;

    private CitizenRepository repoCitizen = new CitizenRepository();

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public boolean validateOneCitizen(Citizen currentCitizen) {
        List<Citizen> citizens = repoCitizen.getAll();
        int counter = 0;
        for (Citizen citizen : citizens) {
            counter += citizen.getNationality() == currentCitizen.getNationality() ? 1 : 0;
        }
        return counter < MAX_NATIONALITY_MEMBERS;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public boolean validateArrayOfCitizens(Citizen[] currentCitizens) {
        List<Citizen> citizens = repoCitizen.getAll();
        int counter = 0;
        for (Citizen currentCitizen : currentCitizens) {
            int currentCounter = 0;
            for (Citizen citizen : citizens) {
                currentCounter += citizen.getNationality() == currentCitizen.getNationality() ? 1 : 0;
            }
            for(Citizen citizen : currentCitizens) {
                currentCounter += citizen.getNationality() == currentCitizen.getNationality() ? 1 : 0;
            }
            currentCounter--;
            counter = currentCounter > counter ? currentCounter : counter;
        }
        return counter < MAX_NATIONALITY_MEMBERS;
    }

}
