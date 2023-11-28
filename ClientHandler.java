import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            username = in.readLine();
            ChatServer.broadcast(username + " has joined the chat.", this);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/private")) {
                    String[] parts = message.split(" ", 3);
                    if (parts.length == 3) {
                        String receiverUsername = parts[1];
                        String privateMessage = parts[2];
                        ChatServer.sendPrivateMessage(privateMessage, receiverUsername, this);
                    } else {
                        sendMessage("Invalid private message format. Use /private username message");
                    }
                } else {
                    ChatServer.broadcast(username + ": " + message, this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }

    String getUsername() {
        return username;
    }
}
