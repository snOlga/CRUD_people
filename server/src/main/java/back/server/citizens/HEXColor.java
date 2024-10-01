package back.server.citizens;

import java.util.regex.*;

import jakarta.persistence.Embeddable;

@Embeddable
public class HEXColor {
    
    private String hexColor = "#000000";

    public HEXColor() {
    }

    public HEXColor(String hexString) throws ColorFormatException {
        this.setHexColor(hexString);
    }

    public void setHexColor(String hexColor) throws ColorFormatException {
        Pattern pattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
        Matcher matcher = pattern.matcher(hexColor);
        if (!matcher.matches())
            throw new ColorFormatException(hexColor);
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }
    
    @Override
    public String toString() {
        return getHexColor();
    }
}
