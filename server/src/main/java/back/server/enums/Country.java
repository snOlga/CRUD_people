package back.server.enums;

public enum Country {
    RUSSIA("RUSSIA"),
    UNITED_KINGDOM("UNITED_KINGDOM"),
    FRANCE("FRANCE"),
    VATICAN("VATICAN"),
    SOUTH_KOREA("SOUTH_KOREA");

    public final String label;

    private Country(String label) {
        this.label = label;
    }
}
