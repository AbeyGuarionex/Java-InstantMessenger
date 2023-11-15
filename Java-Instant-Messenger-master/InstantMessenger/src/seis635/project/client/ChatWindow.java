package seis635.project.client;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		frame = new JFrame("Chatting with " + recipient);
		frame.setSize(600, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(viewFrame);
		 frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		 frame.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosing(WindowEvent e) {
			        // Handle window closing here (e.g., clean up resources)
			        // You might want to confirm if the user really wants to close the window
			        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this chat? Messages will NOT be saved", "Confirm Close", JOptionPane.YES_NO_OPTION);
			        if (option == JOptionPane.YES_OPTION) {
			            // Perform cleanup or other actions before closing
			        	
			        	closingMessageOnExit(recipient);
			            frame.dispose(); // Close the window
			        } else if (option == JOptionPane.CLOSED_OPTION) {
			            // If the user closes the dialog without selecting "Yes" or "No"
			            // Make the window visible again
			            e.getWindow().setVisible(true);
			        }
			    }
			});

		
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
        sendButton.setPreferredSize(new Dimension(125, 40)); // Adjust the button size
        sendButton.setBackground(new Color(50, 150, 255)); // Set a custom background color
        chatWindow.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        sendButton.setForeground(Color.WHITE); // Set text color
        sendButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        sendButton.setFocusPainted(false); // Remove focus border

        msgWindow = new JTextArea();
        msgWindow.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
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
		
		msgWindow.requestFocusInWindow();
		
		sendButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String tempMsg = msgWindow.getText();
				msgWindow.setText("");
				
				
				if(tempMsg == null || tempMsg.trim() == "" || tempMsg.isEmpty()) {
					
					 showErrorDialog("Please enter a message before sending.");
					 msgWindow.requestFocusInWindow();
				}
				else {
					sendMessage(recipient, tempMsg);
					msgWindow.requestFocusInWindow();
				}
				
			}});
		
		

	}
	
	public void sendMessage(String recipient, String message) {
	    Date time = new Date();
	    String trimmedMessage = message.trim();  // Trim the message
	    chatWindow.append(ChatClient.getUsername() + " [" + sdf.format(time)
	            + "]: " + trimmedMessage + "\n");
	    ChatClient.sendMessage(recipient, trimmedMessage, Message.MESSAGE);
	}
	
	public void receiveMessage(String sender, String message){
		Date time = new Date();
		chatWindow.append(sender + " [" + sdf.format(time) + "]: " 
				+ message + "\n");
		
		 // Scroll to the end of the text
	    chatWindow.setCaretPosition(chatWindow.getDocument().getLength());
	}
	
	public void closingMessageOnExit(String recipient) {
	    String exitMessage =   (ChatClient.getUsername() + " has lef the chat." );
	    ChatClient.sendMessage(recipient, exitMessage, Message.MESSAGE);
	}
	

	// Add this method to display an error dialog
		private void showErrorDialog(String errorMessage) {
		    // You can customize this method to show the error message in a dialog box
		    // For simplicity, it uses a JOptionPane here
		    JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
		}
}
