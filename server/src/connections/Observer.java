package connections;

public abstract class Observer {
    protected Subject subject;
    protected Server server;
    public abstract void update();
}
