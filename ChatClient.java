import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            String serverIP = "localhost";
            int serverPort = 1311;

            try (Socket socket = new Socket(serverIP, serverPort)) {
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

                String message = "";
                while (true) {
                    message = consoleReader.readLine();
                    if (message.startsWith("/private")) {
                        String[] parts = message.split(" ", 3);
                        if (parts.length == 3) {
                            String receiverUsername = parts[1];
                            String privateMessage = parts[2];
                            out.println("/private " + receiverUsername + " " + privateMessage);
                        } else {
                            System.out.println("Invalid private message format. Use /private username message");
                        }
                    } else {
                        out.println(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
