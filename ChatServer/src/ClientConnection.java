import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable
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
            
            while(true)
            {
            	if(receivedMessage == null)
            	{
	            	try{
	            		receivedMessage = receive();
	            	} catch(IOException e){
	            		logError("IOException caught while receiving input");
	            		break;
	            	}
            	}
            	else
            	{
            		try{
            			this.wait();
            		}
            		catch (InterruptedException e) {
            			logError("InterruptedException caught while waiting");
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
    }
    
    public String getReceivedMessage()
    {
    	return this.receivedMessage;
    }
    
    public void resetMessage()
    {
    	receivedMessage = null;
    	this.notify(); // Notifys this waiting thread
    }
 
//	private void log(String message){
//		System.out.println("Server: " + message);
//	}

	private void logError(String message){
		System.err.println("Server: " + message);
	}    
}
