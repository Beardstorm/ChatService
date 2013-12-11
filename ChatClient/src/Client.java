import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Subject
{
	private Socket connectionToServer = null;
	private PrintWriter outStream = null;
	private BufferedReader inStream = null;

	public void connect(String serverAddress, int serverPort) throws IOException 
	{
		log("Connecting to the most amazing server ever being made...");

		// Creats a Socket och makes it connect to the server
		connectionToServer = new Socket(serverAddress, serverPort);

		outStream = new PrintWriter(new OutputStreamWriter(
				connectionToServer.getOutputStream()));
		inStream = new BufferedReader(new InputStreamReader(
				connectionToServer.getInputStream()));

		log("Connected!");
	}

	// Closing the connection to the server
	public void close() 
	{
		log("Closing connection.");
		try {
			inStream.close();
			outStream.close();
			connectionToServer.close();
		} 
		catch (IOException e) {
			logError("Failed to properly close the connection. " + e.getMessage());
		}

		log("Connection closed.");
	}

	// Writes messages which are then to be sent to the server
	public void send(String message) 
	{
		log("Sending...");
		outStream.println(message);
		outStream.flush();
		log("Message sent.");
	}

	// Reads the messages coming from the server and calls for notifyObservers in order to update
	public void receive() {
		
		new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				while(true)
				{
					log("Receiving...");
					String receivedMessage = "";
					
					try {
						receivedMessage = inStream.readLine();
						log("Message received: " + receivedMessage);
						notifyObservers(receivedMessage); // Updates Observer with the reference of receivedMessage
					} 
					catch (IOException e) {
						logError("Receive failed! " + e.getMessage());
					}
				}
			}
		}).start();
	}
	private void log(String message) {
		System.out.println("Client: " + message);
	}
	private void logError(String message) {
		System.err.println("Client: " + message);
	}
	
}
