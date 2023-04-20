package connections;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    public void start(Integer portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server listening on address " + serverSocket.getInetAddress());
            System.out.println("Server started on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);

                clientHandler.setSid(clients.size());
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
            for (ClientHandler client : clients) {
                client.stopHandler();
            }
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.send(message);
            //System.out.println(client.getSocket().getPort());
        }
    }

    public void sendMessage(Integer Sid, String message){
        clients.get(Sid).send(message);
    }

    public void addSubject(Integer id){
        this.subjects.add(new Subject(id));
    }

    public Server getServerInstance(){
        return this;
    }

    public void updateState(Integer id, String state){
        //System.out.println("Size" + subjects.size());
        for (Subject subject : subjects){
            //System.out.println("id subject: " + subject.getId() + "id entrante: " + id);
            if(subject.getId().equals(id)){
                subject.setState(state);
                //System.out.println("Update");
            }
        }
    }

    private class ClientHandler extends Thread {

        private Integer sid;


        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void send(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out.println(clients.size()-1);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    //Si el cliente envia J, es un jugador, se crea un Sujeto al que observar, y también se crea un Observador a si mismo.
                    if(inputLine.equals("j")){
                        addSubject(sid);
                        PlayerObserver playerObserver = new PlayerObserver(subjects.get(subjects.size()-1), getServerInstance(), sid);
                    }
                    //Si el cliente envia e#, se trata de crear un Observador con el # recibido.
                    if(inputLine.charAt(0) == 'e'){
                        try{
                            Integer tempid = Integer.parseInt(inputLine.substring(inputLine.indexOf("e")+1));
                            PlayerObserver playerObserver= new PlayerObserver(subjects.get(tempid), getServerInstance(), sid);
                        }catch (Exception e){
                            send("Error handling spectator request, please restart");
                            clients.remove(this);
                            stopHandler();
                        }
                    }
                    System.out.println("Received message from client " + clientSocket + ": " + inputLine);
                    //out.println("Echo: " + inputLine);
                    //broadcast("Client " + clientSocket + " says: " + inputLine);
                    //broadcast("Got yah >:D");
                    //out.println("Got yah >:)");
                }
            } catch (IOException e) {
                System.err.println("Error handling client " + clientSocket + ": " + e.getMessage());
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                    clients.remove(this);
                } catch (IOException e) {
                    System.err.println("Error closing client " + clientSocket + ": " + e.getMessage());
                }
            }
        }

        public void stopHandler() {
            try {
                in.close();
                out.close();
                clientSocket.close();
                interrupt();
            } catch (IOException e) {
                System.err.println("Error stopping client handler " + this + ": " + e.getMessage());
            }
        }

        public Socket getSocket(){
            return this.clientSocket;
        }

        public void setSid(Integer sid) {
            this.sid = sid;
        }

        public Integer getSid(){
            return this.sid;
        }


    }
}