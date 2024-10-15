package back.server.citizens.exceptions;

public class UnrealHumanHeightException extends Exception {
    public UnrealHumanHeightException(String message) {
        super("Unreal human height: " + message);
    }
}
