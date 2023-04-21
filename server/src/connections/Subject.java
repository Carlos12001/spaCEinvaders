package connections;

import game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * Subject class
 * This class is the main class of the subject
 */
public class Subject {
    /**
     * This is the list of observers
     */
    private List<Observer> observers = new ArrayList<>();

    /**
     * This is the state of the subject
     */
    private String state;

    /**
     * This is the id of the subject
     */
    private Integer id;

    /**
     * This is the game of the subject
     */
    private final Game game;

    /**
     * This is the executor of the subject
     */
    private final ScheduledExecutorService executor;

    /**
     * This is the constructor of the class
     * @param id This is the id of the subject
     */
    public Subject(Integer id){
        this.id = id;
        this.game = new Game();
        this.game.startGameLoop();
        executor = Executors.newSingleThreadScheduledExecutor();
        loop();
    }

    /**
     * This is the loop of the subject
     */
    public void loop(){
        executor.scheduleAtFixedRate(() -> {
            if (game.gameOver.get()){
                executor.shutdown();
                System.out.println("Game Over " + id);
                return;
            }
            getState();
            notifyAllObservers();
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    /**
     * This is the method that set the state of the subject
     * @param state This is the state of the subject
     */
    public void setState(String state){
        this.game.setAction(state);
        //aqui se hace manualmente el update
        getState();
        notifyAllObservers();
    }

    /**
     * This is the method that get the state of the subject
     * @return String This is the state of the subject
     */
    public String getState(){
        String s = this.game.getStatus();
//        System.out.println(s);
        return s;
    }

    /**
     * This is the method that get the id of the subject
     * @return Integer This is the id of the subject
     */
    public Integer getId(){
        return id;
    }

    /**
     * This is the method that add an observer to the subject
     * @param observer This is the observer to add
     */
    public void addObserver(Observer observer){
        //System.out.println("Obs Agrgado");
        observers.add(observer);
    }

    /**
     * This is the method that remove an observer to the subject
     * @param observer This is the observer to remove
     */
    public void notifyAllObservers(){
        observers.forEach(x -> x.update());
    }

    /**
     * This is the method that remove an observer to the subject
     * @param observer This is the observer to remove
     */
    public void printObservers(){
        observers.forEach(x -> System.out.println(x.subject));
    }
}
