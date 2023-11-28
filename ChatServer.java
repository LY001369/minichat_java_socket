import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    // cài đặt cổng port
    private static final int PORT = 1311;
    // Tạo một dach sách các đối tượng thuộc kiểu ClientHandler
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server is listening on port " + PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket);

                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void sendPrivateMessage(String message, String receiverUsername, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender && client.getUsername().equals(receiverUsername)) {
                client.sendMessage(sender.getUsername() + " (private): " + message);
                sender.sendMessage("To " + receiverUsername + " (private): " + message);
                return;
            }
        }
        sender.sendMessage("User " + receiverUsername + " not found or is not online.");
    }

    public static void broadcast(String string, ClientHandler clientHandler) {
    }
}
