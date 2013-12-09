

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable
{
	
	private Socket socket = null;
	private PrintWriter out;
	private BufferedReader in;
	 
	// create with a socket
    public ServerConnection(Socket socket){
        this.socket = socket;
    }
    
    // write message to the stream going to the client
	public void send(String message){
		out.println(message);
		out.flush();
	}
	
	// read a message from the stream coming in from the client
	public String receive()
	{
		String receivedMessage = "";
		
		try {
			receivedMessage = in.readLine();
		} 
		catch (IOException e) {
			System.err.println("Receive failed! " + e.getMessage());
		}
		
		return receivedMessage;
	}
	
     
    public void run() 
    {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           
            // afterwards, close the socket
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
