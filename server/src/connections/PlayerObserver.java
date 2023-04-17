package connections;

import java.util.ArrayList;
import java.util.List;

public class PlayerObserver extends Observer{
    Integer sid;
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
