import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame implements Observer
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInputMessage;
	private JPanel inputPanel;
	private TextArea textArea;
	private JPanel panel;
	private Client client;

	public GUI(Client client) 
	{
		this.client = client;
		client.setObserver(this);
		
		// Set window preferences
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 260);
		
		// add content to the window
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);

		// Set panel which holds the text area for messages
		JPanel incomingPanel = new JPanel();
		incomingPanel.setMaximumSize(new Dimension(450, 200));
		incomingPanel.setPreferredSize(new Dimension(10, 200));
		incomingPanel.setMinimumSize(new Dimension(10, 200));
		incomingPanel.setLayout(new BoxLayout(incomingPanel, BoxLayout.X_AXIS));
		contentPane.add(incomingPanel);
	
		// add the text-area-panel to parent panel. 
		incomingPanel.add(panel);

		// Create text-area
		textArea = new TextArea();
		textArea.setPreferredSize(new Dimension(200, 201));
		textArea.setMaximumSize(new Dimension(2200, 200));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setEditable(false);
		textArea.setMinimumSize(new Dimension(200, 201));
		incomingPanel.add(textArea);

		// new panel for the message text-field + send button
		inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

		txtInputMessage = new JTextField();
		txtInputMessage.setMaximumSize(new Dimension(2147483647, 30));
		txtInputMessage.setToolTipText("Message");
		txtInputMessage.setColumns(10);
		txtInputMessage.setEnabled(true);
		txtInputMessage.setText("");
		
		// add
		contentPane.add(inputPanel);
		inputPanel.add(txtInputMessage);
		
		// The send button
		final JButton btnSend = new JButton("Send");
		btnSend.setEnabled(true);
		
		inputPanel.add(btnSend);
		
		// Add actions to text-field, making the Enter-button send messages.
		txtInputMessage.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String message = txtInputMessage.getText().toString();
					txtInputMessage.setText("");
					sendMessage(message);
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		// Send-button action-listener
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					// set "message" to the message actually typed into the text-field.
					String message = txtInputMessage.getText().toString();
					txtInputMessage.setText("");
					
					// send it!
					sendMessage(message);
			}
		});
	}
	
	// SendMessage method that wants a message, gets a message and sends that message.
	public void sendMessage(String message)
	{
		client.send(message);
	}
	
	// When a message is received by the server it shows in the text-area.
	public void update(String message) 
	{
		textArea.append(message + System.lineSeparator());
		textArea.revalidate();
	}
}
