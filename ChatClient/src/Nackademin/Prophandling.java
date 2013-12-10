package Nackademin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Prophandling {

	private Properties prop;

	public void writerProperties(String fileName) {
		prop = new Properties();
		try {
			prop.setProperty("datorname", fileName);
			prop.setProperty("port", fileName);
			prop.store(new FileOutputStream(fileName), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readProperties(String fileName) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printProperty(String propName) {
		System.out.println(prop.getProperty(propName));
	}

	public static void main(String[] args) {

		Prophandling pw = new Prophandling();
		Prophandling pr = new Prophandling();
		pw.writerProperties("config.properties");
		pr.readProperties("config.properties");
		pr.printProperty("port");
		pr.printProperty("computerName");
	}

}
