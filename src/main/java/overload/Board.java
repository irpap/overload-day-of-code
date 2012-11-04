package overload;

import java.util.LinkedList;

public class Board {
    private Cell[][] state;

    private Board(Cell[][] cells) {
        state = cells;
    }

    public static Board emptyBoard(Size size) {
        Cell[][] cells = new Cell[size.getSize()][size.getSize()];
        initialiseCells(cells);
        return new Board(cells);
    }

    private static void initialiseCells(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = Cell.emptyCell();
            }
        }
    }

    public boolean allCellsAreSameColor() {
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

    public void placePieceAt(CellToTakeOver cellToTakeOver) {
        this.setColorAt(cellToTakeOver.position, cellToTakeOver.newColor);
        this.incrementCountAt(cellToTakeOver.position);
    }

    private void incrementCountAt(Position position) {
        state[position.x][position.y].incrementCount();
    }

    private void setColorAt(Position position, Color color) {
        state[position.x][position.y].setColor(color);
    }

    public void empty(Position position) {
        this.setColorAt(position, Color.NONE);
        this.resetCountAt(position);
    }

    private void resetCountAt(Position position) {
        state[position.x][position.y].resetCount();
    }


    public LinkedList<CellToTakeOver> neighboringCellsToTakeOver(CellToTakeOver cellToTakeOver) {
        LinkedList<CellToTakeOver> neighbors = new LinkedList<CellToTakeOver>();
        Position position = cellToTakeOver.position;
        for (int[] diff : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
            Position neighborPosition = new Position(position.x + diff[0], position.y + diff[1]);
            if (withinBounds(neighborPosition)) {
                neighbors.add(cellToTakeOver.copyWithNewPosition(neighborPosition));
            }
        }
        return neighbors;
    }

    private boolean withinBounds(Position position) {
        return position.x >= 0 && position.x < state.length && position.y >= 0 && position.y < state.length;
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
        return x == 0 || x == state.length - 1;
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