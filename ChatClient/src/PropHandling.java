import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropHandling {

	// Great a properties object
	private Properties prop;

	// Great a reading method for at collect port number and localhost
	public void readProperties(String fileName) {
		prop = new Properties();
		try {
			// collect properties from project root folder
			prop.load(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Great String method for property
	public String getProperty(String propName) {
		return prop.getProperty(propName);
	}
}
