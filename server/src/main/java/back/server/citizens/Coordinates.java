package back.server.citizens;

public class Coordinates {
    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    private Coordinates() {
    }

    public Coordinates(String xStr, String yStr) {
        this.x = Float.parseFloat(xStr);
        this.y = Float.parseFloat(yStr);
    }

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
