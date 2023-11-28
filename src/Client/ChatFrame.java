package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatFrame {

    private JFrame frame;
    private JList<String> chatList;
    private DefaultListModel<String> chatListModel;

    private JTextArea mesArea;
    private JTextField mesField;
    private JButton sendButton;

    public ChatFrame(){
        // Thiết lập khung chat
        frame = new JFrame("MINI CHAT BOX");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

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
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        ChatFrame frchat = new ChatFrame();
    }
}
