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

                                        //Display player move
                                        document.getElementById("cell" + playerCell).innerText = "O";
                                        document.getElementById("cell" + playerCell).disabled = true;

                                        //Display computer move
                                        document.getElementById("cell" + computerCell).innerText = "X";
                                        document.getElementById("cell" + computerCell).disabled = true;
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

                int computerCell = game.compMove();

                ctx.result(cell + "," + computerCell);
            });

        }).start(7070);
    }
}