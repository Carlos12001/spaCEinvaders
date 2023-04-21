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


        Server server = new Server();
        //server.start(25565);

        Thread serverThread = new Thread(() -> {
            server.start(25565);
        });
        serverThread.start();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Escribe algo (escribe 'fin' para terminar):");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.equals("fin")) {
                break;
            }

            server.updateState(0, input);
        }

        scanner.close();

//        Game game = new Game();
//        game.startGameLoop();
//
//        //----------
//        Scanner scanner = new Scanner(System.in);
//        String input = "";
//        while (!game.gameOver.get()) {
//            input = scanner.nextLine();
//            game.setAction(input);
//            System.out.println(game.getStatus()); // aqui manda al cliente cierto 25ms
//        }
    }
}