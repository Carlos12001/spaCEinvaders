package game;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private Boolean gameOver = false;

    private final ScheduledExecutorService executor;

    private Integer alienMoveTimer = 0;

    private Integer alienShootTimer = 0;

    private Integer ufoMoveTimer = 0;

    private Integer shootMoveTimer = 0;


    public Game() {
        matrixGame = new MatrixGame();
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void printMatriz(){
        this.matrixGame.printMatriz();
    }

    public void checkGameOver(){
        this.gameOver = (playerLives <= 0) || matrixGame.aliensArrived() ;
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
        matrixGame.printMatriz();
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
        } else if (action.equals("killall")) {
            matrixGame.killAllAliens();
        } else if (firstChar=='p') {
            matrixGame.printMatriz();
        }
        else {
            System.out.println("Error: "+ "No existe la accion");
        }
        action = "";
    }

    public void startGameLoop() {
        executor.scheduleAtFixedRate(() -> {
            checkGameOver();
            if (gameOver) {
                System.out.println("Game Over");
                System.out.print(getStatus());
                executor.shutdown();
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
            printMatriz();
            System.out.print(getStatus());
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
                "\"gameOver\":" + gameOver +
                ",\"playerLives\":" + playerLives +
                ",\"playerScore\":" + playerScore +
                ",\"matrizGame\":" + matrixGame.toString() +
                "}";
    }
    public String getStatus() {
        StringBuilder result = new StringBuilder();
        result.append((gameOver ? String.valueOf(1) : String.valueOf(0)));
        result.append(",");
        result.append(playerLives);
        result.append(",");
        result.append(playerScore);
        result.append(",");
        result.append(matrixGame.getMatrizString());
        return   result.toString();
    }

}

/*
 existen cosas que son asincronicas y que el game
 tiene que ser capaz de manejarlas cuando le digan
 que tiene que hacer algo
 - cuando el jugador dispara
 - cuando el jugador mueve la nave
 - el crear una ufo, tiene que revisar si ya existe un ufo

 Tambien Game tiene que ser capaz devolver la matriz
 del juego, cuando pierde el jugador, las vidas del jugador y el score del
 jugador.

 del cliente seria si el jugador quiere mover la nave -1 izquierda 0 no es moverse
    1 derecha, si quiere disparar,
 si quiere disprar,




{0, 0, 0}

 Inicie Server

 Tengo el puerto 8080

 Esperando cliente jugador

 ---> Se conecta un cliente jugador para jugar


 - Servirdor ok

 manda info

    server recibir informacion de la consola

    Cliente-1 ufo posicion fila 0

    Cliente-2 ufo posicion fila 1

    --- Diseno de Patron Observer ---
    {
    Cliente: jugador
    Tiene un Game
    8081
    },

    {
    Cliente: jugador
    Tiene un Game
    8082
    },
    {
    Cliente: espectador
    Ve lo del cliente 1
    8083
    }


    if j si quire ser jugador
    -> crea la conexion
    if e si quiere ser espectador
        pregunta si cual jugador quiere ver
        -> crea la conexion si existe el jugador
        exit(-1) si no existe el jugador

    jugador escriba algo
    espectador lee lo que escribe el jugador

    l -> izquierda
    r -> derecha
    s -> disparar
 */
