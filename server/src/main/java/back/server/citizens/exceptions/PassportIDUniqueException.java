package back.server.citizens.exceptions;

public class PassportIDUniqueException extends Exception {
    public PassportIDUniqueException(String messege) {
        super("Passport ID is not unique: " + messege);
    }
}
