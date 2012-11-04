package overload;


import java.util.LinkedList;

public class Game {
    private int movesPlayed = 0;
    private final Board board;

    public Game(Size size) {
        this.board = Board.emptyBoard(size);
    }

    public boolean hasWinner() {
        return movesPlayed > 1 && board.isAllSameColor();
    }

    public Game play(Color color, Position position) {
        movesPlayed++;
        if (board.positionIsBelowThreshold(position))
            board.placePieceAt(color, position);
        else {
            board.empty(position);
            explodePieceToNeighbors(color, position);
        }
        return this;
    }

    private void explodePieceToNeighbors(Color pieceColor, Position piecePosition) {

        LinkedList<PositionAndColor> neighbors = board.neighborsOf(new PositionAndColor(piecePosition, pieceColor));
        while (!neighbors.isEmpty()) {
            PositionAndColor top = neighbors.removeFirst();
            Position topPos = top.position;
            Color topColor = top.color;
            board.placePieceAt(topColor, topPos);

            if (board.countAt(topPos) == board.thresholdOf(topPos)) {
                board.empty(topPos);
                neighbors.addAll(board.neighborsOf(new PositionAndColor(topPos, topColor)));
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
