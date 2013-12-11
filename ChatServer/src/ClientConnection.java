import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection extends Subject implements Runnable
{
	private Socket socket = null;
	private PrintWriter out;
	private BufferedReader in;
	public String username;
	private String receivedMessage;
	 
    public ClientConnection(Socket socket)
    {
        this.socket = socket;
    }
    
	public void send(String message)
	{
		out.println(message);
		out.flush();
	}
	
	public String receive() throws IOException
	{
		return in.readLine();
	}
	 
    public void run() 
    {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           
            send("Enter your username.");
            username = receive();
            receivedMessage = "<"+username+"> has connected";
            notifyObservers();
            
            while(true)
            {
            	if(receivedMessage == null)
            	{
	            	try{
	            		receivedMessage = username + ": " + receive();
	            		notifyObservers();
	            	} catch(IOException e){
	            		logError("IOException caught while receiving input");
	            		break;
	            	}
            	}
            }
            
            // After receiving an IOException, the socket is closed
            socket.close();
        }
        catch (IOException e) 
        {
        	logError("IOException caught while receiving input");
        }
        
        receivedMessage = "<"+username+"> has disconnected";
        notifyObservers();
    }
    
    public String getReceivedMessage()
    {
    	return this.receivedMessage;
    }
    
    public void resetMessage()
    {
    	receivedMessage = null;
    }
 
//	private void log(String message){
//		System.out.println("Server: " + message);
//	}

	private void logError(String message){
		System.err.println("Server: " + message);
	}    
}
