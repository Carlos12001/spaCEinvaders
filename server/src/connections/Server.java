package connections;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    public void start(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server listening on address " + serverSocket.getInetAddress());
            System.out.println("Server started on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
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
        }
    }

    private class ClientHandler extends Thread {
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
                out.println("Hello from server!");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received message from client " + clientSocket + ": " + inputLine);
                    out.println("Echo: " + inputLine);
                    //broadcast("Client " + clientSocket + " says: " + inputLine);
                    broadcast("Got yah >:D");
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
    }
}
