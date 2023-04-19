package game;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {

    private MatrixGame matrixGame;

    private final Integer gameSpeed = 50;

    private final Integer ufoSpeed = 200;

    private Integer alienSpeed = 500;

    private Integer alienPeriodShot = 500;

    private final Integer shotSpeed = 50;

    private Integer playerLives = 3;

    private Integer playerScore = 1000;

    private String action = "";

    private final ScheduledExecutorService executor;

    private Integer alienMoveTimer = 0;

    private Integer alienShootTimer = 0;

    private Integer ufoMoveTimer = 0;

    private Integer shootMoveTimer = 0;

    public final AtomicBoolean gameOver;


    public Game() {
        matrixGame = new MatrixGame();
        executor = Executors.newSingleThreadScheduledExecutor();
        gameOver = new AtomicBoolean(false);
    }

    public void printStatus(){
        String s = "\"gameOver\":" + gameOver.get() +
                ",\"playerLives\":" + playerLives +
                ",\"playerScore\":" + playerScore +
                "\n";

        System.out.println(s);
        matrixGame.printMatriz();
    }

    public void checkGameOver(){
         gameOver.set(playerLives <= 0 || matrixGame.aliensArrived());
    }

    public void setAction(String action) {
        this.action = action;
    }

    private void checkShootResult(Integer shotResult) {
        switch (shotResult){
            case -1:
                playerLives -= 1;
                break;
            case 0:
                break;
            case 1:
                playerScore += 10;
                break;
            case 2:
                playerScore += 20;
                break;
            case 3:
                playerScore += 40;
                break;
            case 14:
                playerScore += generateRandomScore();
                break;
            default:
                break;
        }
    }

    private static Integer generateRandomScore() {
        Integer min = 50;
        Integer max = 200;
        Integer increment = 50;
        Random random = new Random();
        int randomNum = min +
                random.nextInt((max - min) / increment + 1) * increment;
        return randomNum;
    }

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
//            if (action.length()==2){
//                try {
//                int ufoNumber = Integer.parseInt(action.substring(1));
//                    matrizGame.createUFO(ufoNumber);
//                } catch (NumberFormatException e) {
//                    matrizGame.createUFO(0);
//                }
//            }
//            else
//                matrizGame.createUFO(0);
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

    private void updateGame() {

        // check aliens died -> restart matrizGame
        if (matrixGame.aliensDied()){
            playerLives++;
            matrixGame.initializeGameMatriz();
        }

        // move shots
        // move aliens
        alienMoveTimer += gameSpeed;
        if (alienMoveTimer >= alienSpeed) {
            matrixGame.moveAliens();
            alienMoveTimer = 0;
        }

        // shoot aliens

        // move UFO

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

