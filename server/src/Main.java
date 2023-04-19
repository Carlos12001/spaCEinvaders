import connections.*;
import game.Game;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the Server!");
        Game game = new Game();
        game.startGameLoop();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!game.gameOver.get()) {
            input = scanner.nextLine();
            game.setAction(input);
        }

//        Server server = new Server();
//        server.start(25565);
    }
}