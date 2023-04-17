package game;

import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private MatrizGame matrizGame;

    private final int gameSpeed = 50;

    private final int alienSpeed = 500;

    private final int shotSpeed = 100;

    public Game() {
        matrizGame = new MatrizGame();
        printMatriz();
    }

    public Game(int colNumAliens) {
        matrizGame = new MatrizGame(colNumAliens);
    }

    public void printMatriz(){
        this.matrizGame.printMatriz();
    }

    private void startGameLoop() {

    }

    public void testMoveAliens(){
        for (int i = 0; i < 45; i++) {
            matrizGame.updateAliensPosition();
            printMatriz();
        }
    }

}
