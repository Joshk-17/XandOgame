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
    
}
