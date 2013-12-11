import java.io.IOException;

public class StartClass {
	
	public static void main(String[] args) {

		PropHandling propHandler = new PropHandling();
		propHandler.readProperties("config.properties");
		GUI gui = new GUI();
		Client client = new Client(gui);
		gui.setVisible(true);
		
		try {
			client.connect(propHandler.getProperty("IP_address"), Integer.parseInt(propHandler.getProperty("port"))); // Hårdkodat för tillfället :)
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
