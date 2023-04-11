import connection.Connection;
import game.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the Server!");
        Connection.start();
        Game.start();
    }
}