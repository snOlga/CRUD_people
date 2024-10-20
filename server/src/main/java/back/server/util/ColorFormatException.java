package back.server.util;

public class ColorFormatException extends Exception{
    public ColorFormatException(String message) {
        super("Incorrect color format: " + message);
    }
}
