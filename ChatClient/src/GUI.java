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
import javax.swing.JOptionPane;
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JPanel incomingPanel = new JPanel();
		incomingPanel.setMaximumSize(new Dimension(450, 200));
		incomingPanel.setPreferredSize(new Dimension(10, 200));
		incomingPanel.setMinimumSize(new Dimension(10, 200));
		contentPane.add(incomingPanel);
		incomingPanel.setLayout(new BoxLayout(incomingPanel, BoxLayout.X_AXIS));
		
		panel = new JPanel();
		panel.setBorder(null);
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(5, 200));
		panel.setMinimumSize(new Dimension(5, 200));
		panel.setMaximumSize(new Dimension(5, 200));
		incomingPanel.add(panel);

		textArea = new TextArea();
		textArea.setPreferredSize(new Dimension(200, 201));
		textArea.setMaximumSize(new Dimension(2200, 200));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textArea.setEditable(false);
		textArea.setMinimumSize(new Dimension(200, 201));
		incomingPanel.add(textArea);

		inputPanel = new JPanel();

		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

		txtInputMessage = new JTextField();
		txtInputMessage.setMaximumSize(new Dimension(2147483647, 30));
		txtInputMessage.setToolTipText("Message");
		txtInputMessage.setColumns(10);
		
		contentPane.add(inputPanel);
		inputPanel.add(txtInputMessage);
		// Buttons ahead
		final JButton btnSend = new JButton("Send");
		inputPanel.add(btnSend);

		txtInputMessage.setEnabled(true);
		txtInputMessage.setText("");
		btnSend.setEnabled(true);
		
		// Add actions to button
		txtInputMessage.addKeyListener(
		            new KeyListener(){
		                public void keyPressed(KeyEvent e){
		                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
		                		String message = txtInputMessage.getText().toString();
		    					txtInputMessage.setText(""); 
		    					sendMessage(message);
		                    }       
		                }
						@Override
						public void keyTyped(KeyEvent e) {}
						@Override
						public void keyReleased(KeyEvent e) {}
		            }
		        );
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = txtInputMessage.getText().toString();
				if (input == "" ) { 	
					System.out.println("input: (" + input + ")");
					JOptionPane.showMessageDialog(new JFrame(),
							"Could not open file", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String message = txtInputMessage.getText().toString();
					txtInputMessage.setText("");
					sendMessage(message);
				}
			}
		});
	}
	
	public void sendMessage(String message)
	{
		client.send(message);
	}
	
	@Override
	public void update(String message) 
	{
		textArea.append(message + System.lineSeparator());
		textArea.revalidate();
	}
}
