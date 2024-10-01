package back.server.citizens;

public class ColorFormatException extends Exception{
    public ColorFormatException(String messege) {
        super("Incorrect color format: " + messege);
    }
}
