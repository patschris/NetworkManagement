package dd.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/* Database properties imported from database.properties */
public class DbConnection {
	private static String username;
	private static String password;
	private static String driver;
	private static String url;
	private static DbConnection instance = null;
	
	private DbConnection(){
		try {
			File file = new File("src/main/java/dd/database/database.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fileInput);
			fileInput.close();
			username = prop.getProperty("Username");
			password = prop.getProperty("Password");
			driver = prop.getProperty("JDBC_DRIVER");
			url = prop.getProperty("DB_URL");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized DbConnection getInstance(){
		if (instance == null){
			instance = new DbConnection();
		}
		return instance;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}
}