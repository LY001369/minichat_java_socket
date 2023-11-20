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
            // Tạo một ServerSocket ở cổng 1311 và chờ kết nối
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                // Chấp nhận yêu câu kết nối từ clinet
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Tạo 1 đối tượng thuộc lớp ClientHandler với tham số clientSocket (mọi một đối tượng ClientHandler chạy thên 1 luồng tiến trình riêng)
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                // Thêm đối tượng client mới này vào danh sách các client
                clients.add(clientHandler);
                // Bắt đầu chạy luồng clientHandler
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Một hàm kiểu static (phương thức của lớp) với 2 tham số messege và sender. 
    //Có chức năng gửi messge đến tất cả clinet đã kết nối trừ clinet sender được chuyền vào
    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}
