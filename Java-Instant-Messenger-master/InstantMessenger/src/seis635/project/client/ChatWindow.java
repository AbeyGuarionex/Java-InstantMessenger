package seis635.project.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import seis635.project.cmn.Message;

public class ChatWindow {

	private JFrame frame;
	private Container contentPane;
	private JPanel botPanel;
	private Border border;
	private JButton sendButton;
	private JScrollPane msgScroll, chatScroll;
	private JTextArea msgWindow, chatWindow;
	@SuppressWarnings("unused")
	private String recipient;
	
	private SimpleDateFormat sdf;
	
	public ChatWindow(final String recipient, JFrame viewFrame){
		
		this.recipient = recipient;
		sdf = new SimpleDateFormat("HH:mm:ss");
		
		//Create frame, set size, center, and set exit on close
		frame = new JFrame("Chat with " + recipient);
		frame.setSize(600, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(viewFrame);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Create Border
		border = BorderFactory.createLineBorder(Color.gray);
		
		//Create Components
		// Create Components
        chatWindow = new JTextArea();
        chatWindow.setEditable(false);
        chatWindow.setBackground(new Color(240, 240, 240)); // Set a light background color
        chatScroll = new JScrollPane(chatWindow);
        chatScroll.setBorder(BorderFactory.createEmptyBorder()); // Remove the border

        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(100, 40)); // Adjust the button size
        sendButton.setBackground(new Color(50, 150, 255)); // Set a custom background color
        sendButton.setForeground(Color.WHITE); // Set text color
        sendButton.setFocusPainted(false); // Remove focus border

        msgWindow = new JTextArea();
        msgScroll = new JScrollPane(msgWindow);
        msgScroll.setPreferredSize(new Dimension(500, 100)); // Adjust the size
        msgScroll.setBorder(border);

        botPanel = new JPanel();
        botPanel.setLayout((LayoutManager) new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout
        botPanel.add(msgScroll);
        botPanel.add(sendButton);
        botPanel.setBorder(border);

		
		//Set up pane
		contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2, 1));
		contentPane.add(chatScroll);
		contentPane.add(botPanel);
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		
		sendButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String tempMsg = msgWindow.getText();
				msgWindow.setText("");
				sendMessage(recipient, tempMsg);
			}});
	}
	
	public void sendMessage(String recipient, String message){
		Date time = new Date();
		chatWindow.append(ChatClient.getUsername() + " [" + sdf.format(time) 
				+ "]: " + message + "\n");
		ChatClient.sendMessage(recipient, message, Message.MESSAGE);
	}
	
	public void receiveMessage(String sender, String message){
		Date time = new Date();
		chatWindow.append(sender + " [" + sdf.format(time) + "]: " 
				+ message + "\n");
	}
}
