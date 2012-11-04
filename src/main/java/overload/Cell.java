package overload;

public class Cell {
    private Color color;
    private int count;

    public static Cell emptyCell() {
        return new Cell(Color.NONE, 0);
    }

    private Cell(Color color, int count) {
        this.color = color;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void incrementCount() {
        count++;
    }

    public void resetCount() {
        count = 0;
    }

    public String toString() {
        switch (color) {
            case BLACK:
                return "B";
            case WHITE:
                return "W";
            default:
                return " ";
        }
    }
}
