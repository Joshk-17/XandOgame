import io.javalin.Javalin;

public class WebApp {

    public static void main(String[] args) {

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
                                <td><button onclick="makeMove(7)">7</button></td>
                                <td><button onclick="makeMove(8)">8</button></td>
                                <td><button onclick="makeMove(9)">9</button></td>
                            </tr>
                            <tr>
                                <td><button onclick="makeMove(4)">4</button></td>
                                <td><button onclick="makeMove(5)">5</button></td>
                                <td><button onclick="makeMove(6)">6</button></td>
                            </tr>
                            <tr>
                                <td><button onclick="makeMove(1)">1</button></td>
                                <td><button onclick="makeMove(2)">2</button></td>
                                <td><button onclick="makeMove(3)">3</button></td>
                            </tr>
                        </table>

                        <script>
                            function makeMove(cell) {
                            alert("You clicked square " + cell);
                            }
                        </script>



                    </body>
                    </html> 
                    """;
                ctx.html(html);
            });

        }).start(7070);
    }
}