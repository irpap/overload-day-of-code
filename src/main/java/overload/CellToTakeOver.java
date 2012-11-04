package overload;

public class CellToTakeOver {
    Position position;
    Color newColor;

    CellToTakeOver(Position position, Color color) {
        this.position = position;
        this.newColor = color;
    }

    public CellToTakeOver copyWithNewPosition(Position position) {
        return new CellToTakeOver(position, this.newColor);
    }
}