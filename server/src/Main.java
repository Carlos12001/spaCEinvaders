import connections.*;
import game.Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the Server!");
        Connection.start();
        Game.start();


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

            System.out.println("Lo que escribiste fue: " + input);
            server.updateState(0, input);
        }

        scanner.close();
    }
}