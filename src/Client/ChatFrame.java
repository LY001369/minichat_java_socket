package Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ChatFrame extends JFrame {

    private Socket socket;
    private String serverIP = "localhost";
    private int serverPost = 1311;
    private BufferedReader in;
    private PrintWriter out;

    private JList<String> chatList;
    private DefaultListModel<String> chatListModel;
    private JTextArea mesArea;
    private JTextField mesField;
    private JButton sendButton;

    public ChatFrame(){
        setFrame();
        setVisible(false);
        if (setSocker()) {
            login();
        }

            new Thread(() -> serverResponse()).start();
            sendMessage();
    }

    private void setFrame(){
        // Thiết lập khung chat
        setTitle("MINI CHAT BOX");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);


        // Thiết lập các thành phần của khung chat
        chatListModel = new DefaultListModel<>();
        chatList = new JList<>(chatListModel);
        mesArea = new JTextArea();
        mesField = new JTextField();
        sendButton = new JButton("send");

        // Thiết lập các layout để bố trí khung chat
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel chatPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());
        chatPanel.setLayout(new BorderLayout());

        // Thêm các thành phần vào layout
        leftPanel.add(new JScrollPane(chatList), BorderLayout.CENTER);
        chatPanel.add(mesField, BorderLayout.CENTER);
        chatPanel.add(sendButton, BorderLayout.EAST);
        rightPanel.add(new JScrollPane(mesArea), BorderLayout.CENTER);
        rightPanel.add(chatPanel, BorderLayout.SOUTH);

        // thêm layout vào khung chat
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private boolean setSocker(){
        try {
            socket = new Socket(serverIP, serverPost);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            in = new  BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    private boolean login(){
        LoginFrame loginFrame = new LoginFrame();
        boolean ok = false;
        loginFrame.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginFrame.usernameField.getText();
                char[] password = loginFrame.passwordField.getPassword();
                out.println("#LOGIN#");
                out.println(username);
                out.println(password);
                try {
                    String check = in.readLine();
                    if (check == "1") 
                        ok = true;
                    else 
                        ok = false;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        return ok;
    }

    private void serverResponse() {
        try {
            String Response;
            while ((Response = in.readLine()) != null) {
                addmessegetochat(Response);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void sendMessage(){
        sendButton.addActionListener(e -> {
            String message = mesField.getText();
            out.println(message);
            addmessegetochat(">>> " + message);
            mesField.setText("");
        });
    }

    private void addmessegetochat(String message){
        mesArea.append(message + "\n");
        mesArea.setCaretPosition(mesArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatFrame());
    }
}
