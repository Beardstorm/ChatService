import java.io.IOException;

public class StartClass {
	
	public static void main(String[] args) {

		PropHandling propHandler = new PropHandling();
		propHandler.readProperties("config.properties");

		Client client = new Client(); // Creates a new instance of Client which is called client
		GUI gui = new GUI(client); // New instance of GUI which uses client as a reference
		gui.setVisible(true); // Activates the GUI and makes it visible
		
		try {
			client.connect(propHandler.getProperty("address"), Integer.parseInt(propHandler.getProperty("port"))); // Connects to the server by using the data provided in config.properties
		} 
		catch (IOException e) {
			System.err.println("Failed to connect to the server. " + e.getMessage());
			System.exit(1);
		}
		catch (NumberFormatException e){
			System.err.println("NumberFormatException caught while parsing Port Number" + e.getMessage());
		}
		
		
		client.receive();
	}
}
