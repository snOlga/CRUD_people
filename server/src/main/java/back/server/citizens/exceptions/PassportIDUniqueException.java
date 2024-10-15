package back.server.citizens.exceptions;

public class PassportIDUniqueException extends Exception {
    public PassportIDUniqueException(String message) {
        super("Passport ID is not unique: " + message);
    }
}
