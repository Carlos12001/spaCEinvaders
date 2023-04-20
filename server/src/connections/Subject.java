package connections;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private String state;
    private Integer id;

    public Subject(Integer id){
        this.id = id;
    }

    public void setState(String state){
        this.state = state;
        notifyAllObservers();
    }

    public String getState(){
        return state;
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
