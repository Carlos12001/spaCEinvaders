package connections;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject class
 * This class is the main class of the subject
 */
public class PlayerObserver extends Observer{

    /**
     * This is the subject of the observer
     */
    Integer sid;

    /**
     * This is the subject of the observer
     * @param subject This is the subject of the observer
     * @param server This is the server of the observer
     * @param sid This is the sid of the observer
     */
    public PlayerObserver(Subject subject, Server server, Integer sid){
        this.subject = subject;
        this.subject.addObserver(this);
        this.server = server;
        this.sid = sid;
    }
    @Override
    public void update() {
        server.sendMessage(sid, subject.getState());
    }
}
