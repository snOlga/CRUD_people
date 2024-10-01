package back.server.citizens.exceptions;

public class ColorFormatException extends Exception{
    public ColorFormatException(String messege) {
        super("Incorrect color format: " + messege);
    }
}
