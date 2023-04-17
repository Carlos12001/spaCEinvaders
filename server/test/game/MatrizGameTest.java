package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrizGameTest {
    MatrizGame matrizGame;

    @BeforeEach
    void setUp() {
        matrizGame = new MatrizGame();
    }

    @Test
    void initializeGameMatriz() {
    }

    @Test
    void setMatrixZero() {
    }

    @Test
    void aliensArrived() {
    }

    @Test
    void aliensDied() {
    }

    @Test
    void createUFO() {
    }

    @Test
    void moveUFO() {
    }

    @Test
    void moveShoots() {
    }

    @Test
    void playerWillHit() {
    }

    @Test
    void alienWillDie() {

        matrizGame.printMatriz();
        for (Integer i = 0; i < 53; i++) {
            matrizGame.moveAliens();
            matrizGame.printMatriz();
        }
        System.out.println("Kill All Aliens");
        matrizGame.killAllAliens();
        matrizGame.printMatriz();
        assertEquals(true, matrizGame.aliensDied());
        matrizGame.initializeGameMatriz();
        matrizGame.printMatriz();
    }

    @Test
    void movePlayer() {
        // Move player to the left edge
        for (Integer i = 0; i < 50; i++) {
            matrizGame.movePlayer(-1);
        }
        matrizGame.printMatriz();

        // Check if player is at the left edge
        assertEquals(10, (Integer) matrizGame.getMatriz()[matrizGame.getRowNum() - 1][0]);

        // Move player to the right edge
        for (Integer i = 0; i < 50; i++) {
            matrizGame.movePlayer(1);
        }
        matrizGame.printMatriz();


        // Check if player is at the right edge
        assertEquals(10, (Integer) matrizGame.getMatriz()[matrizGame.getRowNum() - 1][matrizGame.getColNum() - 1]);

        // Move player back to the center
        for (Integer i = 0; i < matrizGame.getColNum() / 2; i++) {
            matrizGame.movePlayer(-1);
        }
        matrizGame.printMatriz();


        // Check if player is at the center
        assertEquals(10, (Integer) matrizGame.getMatriz()[matrizGame.getRowNum() - 1][matrizGame.getColNum() / 2 - 1]);
    }
    @Test
    void createShoot() {
    }

    @Test
    void moveAliens() {
        matrizGame.printMatriz();
        for (Integer i = 0; i < 2*(matrizGame.getColNum()-5+1); i++) {
            System.out.print("Iteration:\t" + i + "\t");
            matrizGame.moveAliens();
            matrizGame.printMatriz();
        }
    }

}