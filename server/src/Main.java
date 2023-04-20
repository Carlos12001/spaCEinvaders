import connections.*;
import game.Game;
import java.util.Scanner;

/*
    * Main class
    * This class is the main class of the server
 */
public class Main {

    /*
     * Main method
     * This method is the main method of the server
     */
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