package game;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/*
    * Game class
    * This class is the main class of the game
 */
public class Game {

    /*
     * This is the matrix of the game
     */
    private MatrixGame matrixGame;

    /*
     * This is the speed of the game
     */
    private final Integer gameSpeed = 50;

    /*
     * This is the speed of the ufo
     */
    private final Integer ufoSpeed = 500;

    /*
     * This is the speed of the aliens
     */
    private Integer alienSpeed = 200;

    /*
     * This is the period of the aliens shoot
     */
    private Integer alienPeriodShoot = 2000;

    /*
     * This is the speed of the shoot
     */
    private final Integer shootSpeed = 100;

    /*
     * This is the timer of the aliens move
     */
    private Integer playerLives = 3;

    /*
     * This is the score of the player
     */
    private Integer playerScore = 0;

    /*
     * This is the action of the player
     */
    private String action = "";

    /*
     * This is the executor of the game
     */
    private final ScheduledExecutorService executor;

    /*
     * This is the timer of the aliens move
     */
    private Integer alienMoveTimer = 0;

    /*
     * This is the timer of the aliens shoot
     */
    private Integer alienShootTimer = 0;

    /*
     * This is the timer of the ufo move
     */
    private Integer ufoMoveTimer = 0;

    /*
     * This is the timer of the shoot move
     */
    private Integer shootMoveTimer = 0;

    /*
     * This is the counter of the aliens killed
     */
    private Integer counterKillAliens = 0;

    /*
     * This is the counter of the aliens killed
     */
    public final AtomicBoolean gameOver;

    /*
     * This is the constructor of the game
     */
    public Game() {
        matrixGame = new MatrixGame();
        executor = Executors.newSingleThreadScheduledExecutor();
        gameOver = new AtomicBoolean(false);
        counterKillAliens = 0;
    }

    /*
     * This is the method that start the game loop
     */
    public void printStatus(){
        String s = "\"gameOver\":" + gameOver.get() +
                ",\"playerLives\":" + playerLives +
                ",\"playerScore\":" + playerScore +
                "\n";

        System.out.println(s);
        matrixGame.printMatriz();
    }

    /*
     * This is the method that start the game loop
     */
    public void checkGameOver(){
         gameOver.set(playerLives <= 0 || matrixGame.aliensArrived());
    }

    /*
     * This is the method that start the game loop
     * @param action is the action of the player
     */
    public void setAction(String action) {
        this.action = action;
    }

    /*
     * This is the method that start the game loop
     * @param shootResult is the result of the shoot
     */
    private void checkShootResult(Integer shotResult) {
        switch (shotResult){
            case -1:
                playerLives -= 1;
                break;
            case 0:
                break;
            case 1:
                counterKillAliens++;
                System.out.println("Alien 1 killed");
                playerScore += 10;
                break;
            case 2:
                counterKillAliens++;
                System.out.println("Alien 2 killed");
                playerScore += 20;
                break;
            case 3:
                counterKillAliens++;
                System.out.println("Alien 3 killed");
                playerScore += 40;
                break;
            case 14:
                Integer points = generateRandomScore();
                System.out.println("UFO points: " + points);
                playerScore += points;
                break;
            default:
                break;
        }
    }

    /*
     * This is the method that start the game loop
     * @return the random score
     */
    private static Integer generateRandomScore() {
        Integer min = 50;
        Integer max = 200;
        Integer increment = 50;
        Random random = new Random();
        int randomNum = min +
                random.nextInt((max - min) / increment + 1) * increment;
        return randomNum;
    }

    /*
     * This is the method that start the game loop
     */
    public void doAction() {
        char firstChar = action.charAt(0);
        // shoot player
        if (firstChar=='s') {
            checkShootResult(matrixGame.shootPlayer());
        // left move player
        } else if (firstChar=='r') {
            matrixGame.movePlayer(1);
        // right move player
        } else if (firstChar=='l') {
            matrixGame.movePlayer(-1);
        // create UFO
        } else if (firstChar=='u') {
            if (action.length()<=2){
                try {
                int ufoNumber = Integer.parseInt(action.substring(1));
                    matrixGame.createUFO(ufoNumber);
                } catch (NumberFormatException e) {
                    matrixGame.createUFO(0);
                }
            }
            else
                matrixGame.createUFO(0);
        } else if (firstChar=='p') {
            printStatus();
        } else if (action.equals("killall")) {
            matrixGame.killAllAliens();
        } else if (action.equals("killgame")) {
            gameOver.set(true);
            executor.shutdown();
            System.out.println("Game Over doAction");
        }
        else {
            System.out.println("Error: "+ "No existe la accion");
        }
        action = "";
        printStatus();
    }

    /*
     * This is the method that start the game loop
     */
    public void startGameLoop() {
        executor.scheduleAtFixedRate(() -> {
            checkGameOver();
            if (gameOver.get()) {
                executor.shutdown();
                System.out.println("Game Over gameloop");
                printStatus();
            } else {
                updateGame();
            }
        }, 0, gameSpeed, TimeUnit.MILLISECONDS);
    }

    /*
     * This is the method that update the game
     */
    private void updateGame() {

        if (counterKillAliens == 10) {
            alienSpeed =  400;
        } else if (counterKillAliens == 20) {
            alienSpeed =  300;
        }

        // check aliens died -> reincia el juego y aumenta una vida
        if (matrixGame.aliensDied()){
            alienMoveTimer = 0;
            alienShootTimer = 0;
            ufoMoveTimer = 0;
            shootMoveTimer = 0;
            counterKillAliens = 0;
            alienSpeed = 500;
            playerLives++;
            matrixGame.initializeGameMatriz();
        }

        // move shots player
        shootMoveTimer += gameSpeed;
        if (shootMoveTimer >= shootSpeed) {
            Integer[] shotsMoveResult;
            shotsMoveResult = matrixGame.moveShootsPlayer();
            for (Integer shot : shotsMoveResult) {
                checkShootResult(shot);
            }
            shotsMoveResult = matrixGame.moveShootsAliens();
            for (Integer shot : shotsMoveResult) {
                checkShootResult(shot);
            }
            shootMoveTimer = 0;
        }

        // move aliens
        alienMoveTimer += gameSpeed;
        if (alienMoveTimer >= alienSpeed) {
            matrixGame.moveAliens();
            alienMoveTimer = 0;
        }


        // shoot aliens
        alienShootTimer += gameSpeed;
        if (alienShootTimer >= alienPeriodShoot) {
            matrixGame.shootAliens();
            alienShootTimer = 0;
        }

        // move UFO
        ufoMoveTimer += gameSpeed;
        if (ufoMoveTimer >= ufoSpeed) {
            Integer result = matrixGame.moveUFO();
            checkShootResult(result);
            ufoMoveTimer = 0;
        }

        // do action
        if (!action.isEmpty())
            doAction();

    }

    @Override
    public String toString() {
        return "{"+
                "\"gameOver\":" + gameOver.get() +
                ",\"playerLives\":" + playerLives +
                ",\"playerScore\":" + playerScore +
                ",\"matrizGame\":" + matrixGame.toString() +
                "}";
    }

    /*
     * This is the method that start the game loop
     * @return the status of the game
     */
    public String getStatus() {
        StringBuilder result = new StringBuilder();
        result.append((gameOver.get() ? String.valueOf(1) : String.valueOf(0)));
        result.append(",");
        result.append(playerLives);
        result.append(",");
        result.append(playerScore);
        result.append(",");
        result.append(matrixGame.getMatrizString());
        return   result.toString();
    }

}

