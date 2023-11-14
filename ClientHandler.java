import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            // Tạo luồn đầu vào từ Client
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Tạo luồn đầu ra đến Client           
            out = new PrintWriter(socket.getOutputStream(), true);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // hàm sẽ thực thì khi đổi tượng thuộc lớp gọi start.
    @Override
    public void run() {
        try {
            // Nhận tin nhắn đầu tiên từ client và gắn nó là username
            String username = in.readLine();
            // Gửi tin nhắn đến các clinet khác thông báo Username đã vào cuộc trò chuyện
            ChatServer.broadcast(username + " has joined the chat.", this);

            // lập lại nhận 1 tin nhắn từ clinet nếu nó khác null thì gửi tin nhắn đó đến các clinet khác
            String message;
            while ((message = in.readLine()) != null) {
                ChatServer.broadcast(username + ": " + message, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();   // đóng luồng đầu vào
                out.close();  // đóng luồn đầu ra
                clientSocket.close(); // đóng kết nối
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức giử tin nhắn đến client
    void sendMessage(String message) {
        out.println(message);
    }
}
