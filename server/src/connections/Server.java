package connections;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server class
 * This class is the main class of the server
 */
public class Server {

    /**
     * This is the server socket
     */
    private ServerSocket serverSocket;

    /**
     * This is the list of clients
     */
    private List<ClientHandler> clients = new ArrayList<>();

    /**
     * This is the list of subjects
     */
    private List<Subject> subjects = new ArrayList<>();

    /**
     * This is the constructor of the class
     */
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

    /**
     * This is the stop method of the class
     */
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

    /**
     * This is the broadcast method of the class
     * @param message This is the message to broadcast
     */
    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.send(message);
            //System.out.println(client.getSocket().getPort());
        }
    }

    /**
     * This is the send message method of the class
     * @param Sid This is the id of the client
     * @param message This is the message to send
     */
    public void sendMessage(Integer Sid, String message){
        clients.get(Sid).send(message);
    }

    /**
     * This is the add subject method of the class
     * @param id This is the id of the subject
     */
    public void addSubject(Integer id){
        this.subjects.add(new Subject(id));
    }

    /**
     * This is the get server instance method of the class
     * @return Server This is the server instance
     */
    public Server getServerInstance(){
        return this;
    }

    /**
     * This is the get subject method of the class
     * @param id This is the id of the subject
     * @return Subject This is the subject
     */
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

    /**
     * This is the get subject method of the class
     */
    private class ClientHandler extends Thread {

        /**
         * This is the socket of the client
         */
        private Integer sid;

        /**
         * This is the socket of the client
         */
        private Socket clientSocket;

        /**
         * This is the output stream of the client
         */
        private PrintWriter out;

        /**
         * This is the input stream of the client
         */
        private BufferedReader in;

        /**
         * This is the constructor of the class
         * @param socket This is the socket of the client
         */
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        /**
         * This is the set sid method of the class
         * @param message This is the id of the client
         */
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
                    else {
                        updateState(sid, inputLine);
                    }
                    System.out.println("Received message from client " + clientSocket + ": " + inputLine);



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

        /**
         * This is the stop handler method of the class
         */
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

        /**
         * This is the get socket method of the class
         * @return Socket This is the socket of the client
         */
        public Socket getSocket(){
            return this.clientSocket;
        }

        /**
         * This is the set sid method of the class
         * @param sid This is the id of the client
         */
        public void setSid(Integer sid) {
            this.sid = sid;
        }

        /**
         * This is the get sid method of the class
         * @return Integer This is the id of the client
         */
        public Integer getSid(){
            return this.sid;
        }


    }
}