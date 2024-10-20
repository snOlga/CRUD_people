package back.server.util;

public class UnrealHumanHeightException extends Exception {
    public UnrealHumanHeightException(String message) {
        super("Unreal human height: " + message);
    }
}
