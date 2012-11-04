package overload;

public enum Threshold {

    CORNER(2),
    SIDE(3),
    MIDDLE(4);

    private int value;

    Threshold(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
