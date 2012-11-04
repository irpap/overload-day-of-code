package overload;

import org.junit.Test;

import static org.junit.Assert.*;

public class OverloadTest {

    @Test
    public void anEmptyBoardHasNoWinner() {
        Game game = new Game(new Size(2));
        assertFalse(game.hasWinner());
    }

    @Test
    public void afterJustTheFirstMoveNooneWins() {
        Game game = new Game(new Size(2));
        Game updatedGame = game.play(Color.WHITE, new Position(0, 0));
        assertFalse(updatedGame.hasWinner());
        assertEquals(updatedGame.getWinner(), Color.NONE);
    }

    @Test
    public void givenA2By2BoardWithAllBlackPiecesThenBlackWins() {
        Game game = new Game(new Size(2));
        Game updatedGame = game.play(Color.BLACK, new Position(0, 0))
                .play(Color.BLACK, new Position(0, 1))
                .play(Color.BLACK, new Position(1, 0))
                .play(Color.BLACK, new Position(1, 1));
        assertTrue(updatedGame.hasWinner());
        assertEquals(Color.BLACK, updatedGame.getWinner());

    }

    @Test
    public void cornerOverloadsAfter2BlackPieces() {
        Game game = new Game(new Size(2));
        Game updatedGame = game
                .play(Color.BLACK, new Position(0, 0))
                .play(Color.BLACK, new Position(0, 0));

        assertTrue(updatedGame.hasWinner());
        assertEquals(Color.BLACK, updatedGame.getWinner());

        assertEquals(updatedGame.colorAt(new Position(0, 0)), Color.NONE);
        assertEquals(updatedGame.colorAt(new Position(0, 1)), Color.BLACK);
        assertEquals(updatedGame.colorAt(new Position(1, 0)), Color.BLACK);

    }

    @Test
    public void sideOverloadsAfter3BlackPieces() {
        Game game = new Game(new Size(3));
        Game updatedGame = game
                .play(Color.BLACK, new Position(1, 0))
                .play(Color.BLACK, new Position(1, 0))
                .play(Color.BLACK, new Position(1, 0));

        assertEquals(updatedGame.colorAt(new Position(1, 0)), Color.NONE);
        assertEquals(updatedGame.colorAt(new Position(0, 0)), Color.BLACK);
        assertEquals(updatedGame.colorAt(new Position(2, 0)), Color.BLACK);
        assertEquals(updatedGame.colorAt(new Position(1, 1)), Color.BLACK);
    }

    @Test
    public void overloadFromMiddleBottomIsChainedToCenter() {
        Game game = new Game(new Size(3));
        Game updatedGame = game
                .play(Color.BLACK, new Position(1, 1))
                .play(Color.WHITE, new Position(2, 1))

                .play(Color.BLACK, new Position(1, 1))
                .play(Color.WHITE, new Position(2, 1))

                .play(Color.BLACK, new Position(1, 1))
                .play(Color.WHITE, new Position(2, 1));

        System.out.println(updatedGame.toString());
        assertEquals(updatedGame.colorAt(new Position(1, 1)), Color.NONE);

        assertEquals(updatedGame.colorAt(new Position(1, 0)), Color.WHITE);
        assertEquals(updatedGame.colorAt(new Position(2, 1)), Color.WHITE);
        assertEquals(updatedGame.colorAt(new Position(2, 2)), Color.WHITE);
        assertEquals(updatedGame.colorAt(new Position(1, 2)), Color.WHITE);

        assertTrue(updatedGame.hasWinner());
        assertEquals(Color.WHITE, updatedGame.getWinner());

    }

}
