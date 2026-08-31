import io.javalin.Javalin;

public class WebApp {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {

            config.routes.get("/", ctx -> {
                ctx.result("Xs and Os Web App");
            });

        }).start(7070);
    }
}