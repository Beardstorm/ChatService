

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server 
{
	private ServerSocket serverSocket  = null;
	private Socket 	connectionToClient = null;
	private ArrayList<ClientConnection> clients = new ArrayList<ClientConnection>();

	// init the listening server socket on the port specified
	public void initiate(int port) throws IOException
	{
		log("Setting up server socket...");
		serverSocket = new ServerSocket(port);
		log("Server socket setup complete.");
	}
	
	// loop forever, accepting connections and starting those in new threads
	
	
	public void waitForConnections() throws IOException
	{
		new Thread(new Runnable(){

			@Override
			public void run() {
				while (true)
				{
					log("Waiting for connections...");
					try {
						connectionToClient = serverSocket.accept();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					ClientConnection tempClient = new ClientConnection(connectionToClient);
					clients.add(tempClient);
					Thread newThread = new Thread(tempClient);
					
					newThread.start();
					
					log("Connection to client established.");
					log("New thread: " + newThread.getName());
				}
			}
			
		});
	}
	
	private void waitForInput() {
		while(true){
			for(ClientConnection c : clients){
				if(c.getReceivedMessage() != null){
					sendMessageToClients(c.username + ": " + c.getReceivedMessage());
					c.resetMessage();
				}
			}
		}
	}
	
	private void sendMessageToClients(String message){
		for(ClientConnection c: clients){
			c.send(message);
		}
	}
	
	// close the server side
	public void close(){
		log("Closing connection.");
		try{
			serverSocket.close();
		}catch(IOException e){
			logError("Failed to properly close the connection. " + e.getMessage());
		}	
		log("Connection closed.");
	}
	
	
	private void log(String message){
		System.out.println("Server: " + message);
	}

	private void logError(String message){
		System.err.println("Server: " + message);
	}
	
	public static void main(String[] args) 
	{
		Server server = new Server();
		
		try {
			server.initiate(52000);
		} 
		catch (IOException e) {
			System.err.println("Failed to setup a server socket. " + e.getMessage());
			System.exit(1);
		}
		
		try {
			server.waitForConnections();
		} 
		catch (IOException e) {
			System.err.println("Failed to setup connection. " + e.getMessage());
			System.exit(1);
		}
		
		server.waitForInput();
		
		server.close();
	
	}
}
