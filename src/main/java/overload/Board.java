package overload;

import java.util.LinkedList;

public class Board {
    private Cell[][] state;
    private final int width;

    private Board(Cell[][] cells) {
        state = cells;
        this.width = cells.length;
    }

    public static Board emptyBoard(Size size) {
        Cell[][] cells = new Cell[size.getSize()][size.getSize()];
        initialiseCells(cells);
        return new Board(cells);
    }

    private static void initialiseCells(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(Color.NONE, 0);
            }
        }
    }

    public boolean isAllSameColor() {
        Color first = state[0][0].getColor();
        for (Cell[] row : state) {
            for (Cell cell : row) {
                if (first == Color.NONE && cell.getColor() != Color.NONE) first = cell.getColor();
                else if (first != cell.getColor() && cell.getColor() != Color.NONE) return false;
            }
        }
        return first != Color.NONE;
    }

    public Color getFirstNonEmptyColor() {
        for (Cell[] row : state) {
            for (Cell cell : row) {
                if (cell.getColor() != Color.NONE) return cell.getColor();
            }
        }
        return Color.NONE;
    }

    public Color colorAt(Position pos) {
        return state[pos.x][pos.y].getColor();
    }

    public int countAt(Position position) {
        return state[position.x][position.y].getCount();
    }

    public void placePieceAt(Color color, Position position) {
        state[position.x][position.y].setColor(color);
        state[position.x][position.y].incrementCount();
    }

    public void empty(Position position) {
        state[position.x][position.y].setColor(Color.NONE);
        state[position.x][position.y].resetCount();
    }


    public LinkedList<PositionAndColor> neighborsOf(PositionAndColor positionAndColor) {
        LinkedList<PositionAndColor> neighbors = new LinkedList<PositionAndColor>();
        Position position = positionAndColor.position;
        for (int[] diff : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
            int x = position.x + diff[0];
            int y = position.y + diff[1];
            if (x >= 0 && x < width && y >= 0 && y < width) {
                Position neighborPosition = new Position(x, y);
                neighbors.add(new PositionAndColor(neighborPosition, positionAndColor.color));
            }
        }
        return neighbors;
    }


    public int thresholdOf(Position position) {
        if (isCorner(position)) return Threshold.CORNER.getValue();
        else if (isSide(position)) return Threshold.SIDE.getValue();
        else return Threshold.MIDDLE.getValue();
    }

    private boolean isCorner(Position position) {
        return isAtEdge(position.x) && isAtEdge(position.y);
    }

    private boolean isSide(Position position) {
        return isAtEdge(position.x) ^ isAtEdge(position.y);
    }

    private boolean isAtEdge(int x) {
        return x == 0 || x == width - 1;
    }

    public boolean positionIsBelowThreshold(Position position) {
        return countAt(position) < thresholdOf(position) - 1;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Cell[] row : state) {
            for (Cell cell : row) {
                builder.append(cell.toString()).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}