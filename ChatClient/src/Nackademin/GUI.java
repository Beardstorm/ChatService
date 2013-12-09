package Nackademin;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class GUI extends JFrame {

	/**
	 * 
	 * ### mainmetod från testprojektet ###
	 * 
	 * 
	 * public static void main(String[] args) throws IOException {
	 * EventQueue.invokeLater(new Runnable() { public void run() { try { GUI
	 * frame = new GUI(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } });
	 * 
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInputMessage;
	private JPanel inputPanel;
	private JButton btnConnect;

	// GET USERS CONNECTED!!!
	private int[] users = { 0, 1, 2, 3, 4, 5, 6, 7 };

	static Random random = new Random(2);
	private String randomUser = "User" + random.nextInt(122 - 65);
	private JList<String> list;
	private TextArea textArea;
	private JPanel panel;

	public GUI() {
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

		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (int i = 0; i < users.length; i++) {
			listModel.addElement(randomUser);
		}

		list = new JList<String>(listModel);
		list.setForeground(Color.BLACK);
		list.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		list.setVisibleRowCount(10);
		list.setSelectedIndex(-1);
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		list.setBackground(SystemColor.window);
		list.setMinimumSize(new Dimension(100, 199));
		list.setPreferredSize(new Dimension(100, 199));
		list.setMaximumSize(new Dimension(100, 199));
		incomingPanel.add(list);

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

		txtInputMessage.setEnabled(false);
		txtInputMessage.setText("");
		btnSend.setEnabled(false);

		btnConnect = new JButton("Connect");
		inputPanel.add(btnConnect);
		btnConnect.setMinimumSize(new Dimension(170, 29));
		btnConnect.setMaximumSize(new Dimension(170, 29));
		btnConnect.setPreferredSize(new Dimension(170, 29));
		btnConnect.setEnabled(true);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnConnect.getText() == "Connect") {
					btnConnect.setText("Disconnect");
					txtInputMessage.setEnabled(true);
					btnSend.setEnabled(true);
				} else {
					textArea.setText("");
					textArea.repaint();
					btnConnect.setText("Connect");

					txtInputMessage.setEnabled(false);
					btnSend.setEnabled(false);
				}
			}
		});
		



		// Add actions to button
		
		txtInputMessage.addKeyListener(
		            new KeyListener(){
		                public void keyPressed(KeyEvent e){
		                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
		                		String input = txtInputMessage.getText().toString();
		    					textArea.append(input + System.lineSeparator());
		    					txtInputMessage.setText(""); 
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
					textArea.append(message + System.lineSeparator());
					txtInputMessage.setText("");
				}
			}
		});
	}

}
