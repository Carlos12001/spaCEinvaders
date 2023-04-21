package connections;
/**
 * Observer class
 * This class is the main class of the observer
 */
public abstract class Observer {

    /**
     * This is the subject of the observer
     */
    protected Subject subject;

    /**
     * This is the server of the observer
     */
    protected Server server;

    /**
     * This is the update method of the observer
     */
    public abstract void update();
}
