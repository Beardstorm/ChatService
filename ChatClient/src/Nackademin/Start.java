package Nackademin;

import java.io.IOException;

public class Start {
	public static void main(String[] args) {

		Client client = new Client();
		try {
			client.connect("127.0.0.1", 52000); // Hårdkodat för tillfället :)
		} catch (IOException e) {
			System.err.println("Failed to connect to the server. "
					+ e.getMessage());
			System.exit(1);
		}
	}

}
