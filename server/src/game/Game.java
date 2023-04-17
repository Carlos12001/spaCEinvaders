package game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private MatrizGame matrizGame;

    private final Integer gameSpeed = 50;

    private final Integer ufoSpeed = 200;

    private Integer alienSpeed = 500;

    private Integer alienShootTimerWait = 500;

    private final Integer shotSpeed = 50;

    private Integer playerLives = 3;

    private Integer playerScore = 1000;

    private Boolean gameOver = false;

    private final ScheduledExecutorService executor;

    private Integer interationCounter = 0;

    private Integer alienMoveTimer = 0;

    private Integer alienShootTimer = 0;

    private Integer ufoMoveTimer = 0;

    private Integer shootMoveTimer = 0;


    public Game() {
        matrizGame = new MatrizGame();
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void printMatriz(){
        this.matrizGame.printMatriz();
    }

    public void checkGameOver(){
        this.gameOver = (playerLives <= 3) || matrizGame.aliensArrived() ;
    }

    public void startGameLoop() {
        executor.scheduleAtFixedRate(() -> {
//            System.out.println("Iteration: "+ ++interationCounter);
            checkGameOver();
            if (gameOver) {
//                System.out.println("Game Over");
                String s = getStatus();
                System.out.print(s);
                executor.shutdown();
            } else {
                updateGame();
            }
        }, 0, gameSpeed, TimeUnit.MILLISECONDS);
    }

    private void updateGame() {
        // do alien will die
        // do bunkers will hit
        // do player will hit
        // move shots
        // move aliens
        alienMoveTimer += gameSpeed;
        if (alienMoveTimer >= alienSpeed) {
            matrizGame.moveAliens();
            alienMoveTimer = 0;
//            printMatriz();
        }

        // shoot aliens

        // move UFO

        // check aliens died -> restart matrizGame

        // do the shoot player

        // do the move player

        // create UFO

    }

    @Override
    public String toString() {
        return "{"+
                "\"gameOver\":" + gameOver +
                ",\"playerLives\":" + playerLives +
                ",\"playerScore\":" + playerScore +
                ",\"matrizGame\":" + matrizGame.toString() +
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
        result.append(matrizGame.getMatrizString());
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
