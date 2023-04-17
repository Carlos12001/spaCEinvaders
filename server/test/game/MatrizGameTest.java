package game;

import org.junit.jupiter.api.Test;

class MatrizGameTest {

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
    }

    @Test
    void movePlayer() {
    }

    @Test
    void createShoot() {
    }

    @Test
    void moveAliens() {
        MatrizGame matrizGame = new MatrizGame();
        matrizGame.printMatriz();
        for (Integer i = 0; i < 2*(matrizGame.getColNum()-5+1); i++) {
            System.out.print("Iteration:\t" + i + "\t");
            matrizGame.moveAliens();
            matrizGame.printMatriz();
        }
    }

}