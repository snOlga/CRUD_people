package back.server.util;

public class AmountCitizenException extends Exception{
    public AmountCitizenException(String message) {
        super("Amount of citizen with such parameter has already existed: " + message);
    }
}
