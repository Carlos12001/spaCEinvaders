import connections.*;
import game.Game;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the Server!");
        Game game = new Game();
        game.startGameLoop();

//        Scanner scanner = new Scanner(System.in);
//        String input = "";
//
//        while (!input.equals("salir")) {
//
//            input = scanner.nextLine();
//
//            if (input.equals("saludar")) {
//                System.out.println("Hola Mundo!");
//            }
//        }
//
//        scanner.close();
//        System.out.println("Programa terminado.");
//
//        Server server = new Server();
//        server.start(25565);
    }
}