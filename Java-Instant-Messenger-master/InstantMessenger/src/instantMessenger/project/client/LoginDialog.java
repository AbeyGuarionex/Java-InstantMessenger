package instantMessenger.project.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import instantMessenger.project.server.ChatServer;

//Not the prettiest of dialog boxes, but close enough for government work
@SuppressWarnings("serial")
public class LoginDialog extends JDialog {

	private JPanel panel;
	private JLabel jText1;
	private JLabel jText2;
	private JTextField username;
	private JTextField IP;
	private JButton login, cancel;
	
	public LoginDialog(JFrame parent){
		super(parent, "Login", true);
		
		//Create panel and layout for panel
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.weightx = 0.1;
		cs.weighty = 0.1;
		
		//Set up components and add them to panel
		cs.gridy = 0;
		jText1 = new JLabel("Enter your username:");
		panel.add(jText1, cs);
		
		cs.gridy = 0;
		cs.gridx = 1;
		cs.gridwidth = 2;
		username = new JTextField(20);
		panel.add(username, cs);
		
		cs.gridy = 1;
		cs.gridx = 0;
		cs.gridwidth = 1;
		jText2 = new JLabel("Enter IP of Server:");
		panel.add(jText2, cs);
		
		cs.gridy = 1;
		cs.gridx = 1;
		cs.gridwidth = 2;
		IP = new JTextField(20);
		panel.add(IP, cs);
		
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		login = new JButton("Login");
		panel.add(login, cs);
		
		cs.gridx = 1;
		cs.gridy = 2;
		cancel = new JButton("Cancel");
		panel.add(cancel, cs);
		
		this.setContentPane(panel);
		this.setSize(400, 200);
		
		login.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Check if the username is null or empty
		        String enteredUsername = username.getText().trim();
		        if (enteredUsername.isEmpty() || enteredUsername == null) {
		            // Display an error message
		            showErrorDialog("Username cannot be null or empty.");
		        } else {
		         
		                // Set the username and server IP in the ChatClient class
		                ChatClient.setUsername(enteredUsername);
		                ChatClient.setIP(IP.getText().trim());
		                // Close the login dialog
		                dispose();
		        }
		    }
		});

		
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Or change to dispose() if you just want the box to go away
				System.exit(0);
			}});
	}
	
	// Add this method to display an error dialog
		private void showErrorDialog(String errorMessage) {
		    // You can customize this method to show the error message in a dialog box
		    // For simplicity, it uses a JOptionPane here
		    JOptionPane.showMessageDialog(this, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
		}
		

	
}