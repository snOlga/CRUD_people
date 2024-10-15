package back.server.citizens.exceptions;

public class ColorFormatException extends Exception{
    public ColorFormatException(String message) {
        super("Incorrect color format: " + message);
    }
}
