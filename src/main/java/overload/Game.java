package overload;


import java.util.LinkedList;

public class Game {
    private int movesPlayed = 0;
    private final Board board;

    public Game(Size size) {
        this.board = Board.emptyBoard(size);
    }

    public boolean hasWinner() {
        return movesPlayed > 1 && board.allCellsAreSameColor();
    }

    public Game play(Color color, Position position) {
        movesPlayed++;
        if (board.positionIsBelowThreshold(position))
            board.placePieceAt(new CellToTakeOver(position, color));
        else {
            board.empty(position);
            explodePieceToNeighbors(color, position);
        }
        return this;
    }

    private void explodePieceToNeighbors(Color pieceColor, Position piecePosition) {

        LinkedList<CellToTakeOver> neighbors = board.neighboringCellsToTakeOver(new CellToTakeOver(piecePosition, pieceColor));
        while (!neighbors.isEmpty()) {
            CellToTakeOver cellToTakeOver = neighbors.removeFirst();
            Position positionToTakeOver = cellToTakeOver.position;

            board.placePieceAt(cellToTakeOver);

            if (board.countAt(positionToTakeOver) == board.thresholdOf(positionToTakeOver)) {
                board.empty(positionToTakeOver);
                neighbors.addAll(board.neighboringCellsToTakeOver(cellToTakeOver));
            }
        }
    }

    public Color getWinner() {
        if (hasWinner()) {
            return board.getFirstNonEmptyColor();
        } else return Color.NONE;
    }

    public Color colorAt(Position position) {
        return board.colorAt(position);
    }

    public String toString() {
        return board.toString();
    }
}
