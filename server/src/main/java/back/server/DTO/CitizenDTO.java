package back.server.DTO;

import java.util.Map;

import back.server.model.Citizen;
import back.server.util.ColorFormatException;
import back.server.util.PassportIDUniqueException;
import back.server.util.SQLinjectionException;
import back.server.util.UnrealHumanHeightException;

public class CitizenDTO {
    private Citizen citizen;
    private String ownerToken;

    public CitizenDTO(Map<String, String> json)
            throws ColorFormatException, UnrealHumanHeightException, NumberFormatException, PassportIDUniqueException, SQLinjectionException {
        this.citizen = new Citizen(json);
        this.ownerToken = json.get("token");
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public String getOwnerToken() {
        return ownerToken;
    }
}
