import io.javalin.Javalin;

public class WebApp {

    //Create game for web 
    private static TicTacToe game = new TicTacToe();

    public static void main(String[] args) {

        game.init();

        Javalin app = Javalin.create(config -> {

            config.routes.get("/", ctx -> {
                String html = """
                <html>
                    <head>
                        <title>Xs and Os</title>
                    </head>

                    <body>
                        <h1>Xs and Os</h1>

                        <table border="1">
                            <tr>
                                <td><button id="cell7" onclick="makeMove(7)">7</button></td>
                                <td><button id="cell8" onclick="makeMove(8)">8</button></td>
                                <td><button id="cell9" onclick="makeMove(9)">9</button></td>
                            </tr>
                            <tr>
                                <td><button id="cell4" onclick="makeMove(4)">4</button></td>
                                <td><button id="cell5" onclick="makeMove(5)">5</button></td>
                                <td><button id="cell6" onclick="makeMove(6)">6</button></td>
                            </tr>
                            <tr>
                                <td><button id="cell1" onclick="makeMove(1)">1</button></td>
                                <td><button id="cell2" onclick="makeMove(2)">2</button></td>
                                <td><button id="cell3" onclick="makeMove(3)">3</button></td>
                            </tr>
                        </table>

                        <p id="result"></p>

                        <script>
                            function makeMove(cell) {

                                //Send player move to backend
                                fetch("/move/" + cell)
                                    .then(response => response.text())
                                    .then(data => {

                                        //Split the player and computer moves
                                        let moves = data.split(",");

                                        let playerCell = moves[0];
                                        let computerCell = moves[1];
                                        let result = moves[2];

                                        //Display player move
                                        document.getElementById("cell" + playerCell).innerText = "O";
                                        document.getElementById("cell" + playerCell).disabled = true;

                                        //Display computer move
                                        if (computerCell != -1) {
                                            document.getElementById("cell" + computerCell).innerText = "X";
                                            document.getElementById("cell" + computerCell).disabled = true;
                                        }

                                        //Display the result
                                        if (result !== "Playing"){
                                            document.getElementById("result").innerText = result;
                                        }
                                    });
                            }
                        </script>

                    </body>
                    </html> 
                    """;

                ctx.html(html);
            });

            config.routes.get("/move/{cell}", ctx -> {

                int cell = Integer.parseInt(ctx.pathParam("cell"));

                game.playerMoved(cell);

                System.out.println("Player clicked square: " + cell);

                // Check if the player has won
                int result = game.checkForWin();

                if (result == 0) {
                    ctx.result(cell + ",-1,Player wins");
                    return;
                }

                //Check if the game is a draw
                if (result == 2) {
                    ctx.result(cell + ",-1,Draw");
                    return;
                }

                int computerCell = game.compMove();

                // Check if the computer has won
                result = game.checkForWin();

                if (result == 1) {
                    ctx.result(cell + "," + computerCell + ",Computer wins");
                    return;
                }

                //Check if the game is a draw
                if (result == 2) {
                    ctx.result(cell + "," + computerCell + ",Draw");
                    return;
                }

                // If no one has won, continue the game
                ctx.result(cell + "," + computerCell + ",Playing");
            });

        }).start(7070);
    }
}