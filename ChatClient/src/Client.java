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

		// Skapar en Socket och ansluter den till servern
		connectionToServer = new Socket(serverAddress, serverPort);

		outStream = new PrintWriter(new OutputStreamWriter(
				connectionToServer.getOutputStream()));
		inStream = new BufferedReader(new InputStreamReader(
				connectionToServer.getInputStream()));

		log("Connected!");
	}

	// Stänger ner anslutningen till servern
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

	// Skriver meddelande som skickas till servern
	public void send(String message) 
	{
		log("Sending...");
		outStream.println(message);
		outStream.flush();
		log("Message sent.");
	}

	// Läser meddelande som skickas från servern och anropar notifyObservers för att uppdatera
	
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
						notifyObservers(receivedMessage); // Uppdaterar Observer med en referens till receivedMessage
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
