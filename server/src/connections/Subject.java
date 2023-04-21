package connections;

import game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private String state;
    private Integer id;
    private final Game game;
    private final ScheduledExecutorService executor;

    public Subject(Integer id){
        this.id = id;
        this.game = new Game();
        this.game.startGameLoop();
        executor = Executors.newSingleThreadScheduledExecutor();
        loop();
    }

    public void loop(){
        executor.scheduleAtFixedRate(() -> {
            getState();
            notifyAllObservers();
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    public void setState(String state){
        this.game.setAction(state);
        //aqui se hace manualmente el update
        getState();
        notifyAllObservers();
    }

    public String getState(){
        String s = this.game.getStatus();
//        System.out.println(s);
        return s;
    }

    public Integer getId(){
        return id;
    }

    public void addObserver(Observer observer){
        //System.out.println("Obs Agrgado");
        observers.add(observer);
    }

    public void notifyAllObservers(){
        observers.forEach(x -> x.update());
    }

    public void printObservers(){
        observers.forEach(x -> System.out.println(x.subject));
    }
}
