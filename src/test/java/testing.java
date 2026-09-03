import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class testing {

    //Test if player move is valid
    @Test
    public void testInvalidMove() {

        TicTacToe game = new TicTacToe();
        game.init();

        System.setIn(new ByteArrayInputStream("11\n5\n".getBytes()));

        assertEquals(5, game.playerMove());
    }

    //Test if game can detect if the user has won
    @Test
    public void testPlayerWin() {
        TicTacToe game = new TicTacToe();
        game.init();

        // Simulate player moves that lead to a win
        game.playerMoved(1);
        game.playerMoved(2);
        game.playerMoved(3);

        assertEquals(0, game.checkForWin());
    }

    //Test if game can detect if the computer has won
    @Test
    public void testComputerWin() {

        TicTacToe game = new TicTacToe();

        game.init();

        game.compMove();
        game.compMove();
        game.compMove();

        assertEquals(1, game.checkForWin());
    }

    //Test if computer chooses a winning move when available
    //With the given code the computer will choose 5 then 1 as
    //they hold the highest weight. So it should then choose 9 to win
    //the game.
    @Test
    public void testComputerChoosesWinningMove() {

        TicTacToe game = new TicTacToe();

        game.init();

        game.compMove();
        game.compMove();

        assertEquals(9, game.compMove());
    }
}
