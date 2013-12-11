import java.io.IOException;

public class StartClass {
	
	public static void main(String[] args) {

		PropHandling propHandler = new PropHandling();
		propHandler.readProperties("config.properties");

		Client client = new Client(); // Skapar en ny instans av Client kallad client
		GUI gui = new GUI(client); // Ny instans av GUI som har client som referens
		gui.setVisible(true); // Aktiverar GUI och gör det synligt
		
		try {
			client.connect(propHandler.getProperty("address"), Integer.parseInt(propHandler.getProperty("port"))); // Ansluter till servern via de data som finns lagrade i config.properties
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
