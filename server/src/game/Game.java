package game;

import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private MatrizGame matrizGame;

    private Timer timer;

    private final int alienSpeed = 500;

    private final int speedShot = 100;

    public Game() {
        matrizGame = new MatrizGame();
        printMatriz();
//        timer = new Timer();
//        startGameLoop();
    }

    public Game(int colNumAliens) {
        matrizGame = new MatrizGame(colNumAliens);
//        timer = new Timer();
//        startGameLoop();
    }

    public void printMatriz(){
        this.matrizGame.printMatriz();
    }

    private void startGameLoop() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                matrizGame.updateAliensPosition();
                /* Aquí puedes agregar otros métodos para
                 actualizar el estado del juego
                 */
            }
        };

        timer.schedule(task, 0, alienSpeed);
    }

    public void testMoveAliens(){
        for (int i = 0; i < 45; i++) {
            matrizGame.updateAliensPosition();
            printMatriz();
        }
    }

}
