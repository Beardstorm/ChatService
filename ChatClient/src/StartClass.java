import java.io.IOException;

public class StartClass {
	
	public static void main(String[] args) {

		Client client = new Client();
		GUI gui = new GUI(client);
		try {
			client.connect("127.0.0.1", 52000); // Hårdkodat för tillfället :)
		} catch (IOException e) {
			System.err.println("Failed to connect to the server. "
					+ e.getMessage());
			System.exit(1);
		}
		client.receive();
	}
}
