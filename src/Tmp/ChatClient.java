import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {

    //=====================================================
            String ServerIP = "localhost";
            int ServerPort = 1311;
    //=====================================================

            Socket socket = new Socket(ServerIP, ServerPort);

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter your username: ");
            String username = consoleReader.readLine();
            out.println(username);

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String message;
            while (true) {
                message = consoleReader.readLine();
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
