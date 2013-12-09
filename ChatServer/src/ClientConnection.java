

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
	public boolean hasNewMessage = false;
	private String receivedMessage;
	 
	// create with a socket
    public ClientConnection(Socket socket){
        this.socket = socket;
    }
    
    // write message to the stream going to the client
	public void send(String message){
		out.println(message);
		out.flush();
	}
	
	// read a message from the stream coming in from the client
	public String receive() throws IOException
	{
		String receivedMessage = "";
		receivedMessage = in.readLine();
		return receivedMessage;
	}
	 
    public void run() 
    {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           
            send("Enter your username.");
            username = receive();
            
            while(true){
            	if(receivedMessage == null){
	            	try{
	            		receivedMessage = receive();
	            	} catch(IOException e){
	            		e.printStackTrace();
	            		break;
	            	}
            	}
            }
            // afterwards, close the socket
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getReceivedMessage(){
    	return this.receivedMessage;
    }
    
    public void resetMessage(){
    	receivedMessage = null;
    }
}
