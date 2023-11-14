import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {

    //=====================================================
            String ServerIP = "localhost";
            int ServerPort = 1311;
    //=====================================================

            // Tạo một Kết nối đến Server
            Socket socket = new Socket(ServerIP, ServerPort);

            // Tạo luồng đầu vào từ bàn phím (Để nhập từ bàn phím)
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            
            // Tạo luồn đầu ra gửi đến Server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Yêu câu nhập Username
            System.out.print("Enter your username: ");
            String username = consoleReader.readLine();
            
            // Gửi username đến server
            out.println(username);

            // Tạo mới 1 Thread (Một luồng tiến trình mới chạy song song) để nhận các tin nhăn từ Server
            new Thread(() -> {
                try {
                    // Tạo một luồng đầu vào từ Server
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    // lập lại nhận 1 tin nhắn từ Server nếu nó khác null thì in tin nhắn ra màn hình. 
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }      

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String message = "";
            while (true) {
                // Đọc 1 dòng từ bàn phím
                message = consoleReader.readLine();

                // Gửi đến tin nhắn đến server
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
