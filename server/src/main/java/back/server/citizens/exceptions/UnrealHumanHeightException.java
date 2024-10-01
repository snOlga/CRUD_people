package back.server.citizens.exceptions;

public class UnrealHumanHeightException extends Exception {
    public UnrealHumanHeightException(String messege) {
        super("Unreal human height: " + messege);
    }
}
