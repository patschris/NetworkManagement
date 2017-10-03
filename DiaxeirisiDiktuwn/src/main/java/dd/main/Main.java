package dd.main;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import dd.database.DbConnection;
import dd.gui.ImportWindow;

public class Main {
	public static void main(String[] args) {
		/* initialize tha data of the database */
		Connection conn = null;
		Statement stmt=null;
		DbConnection dbc = DbConnection.getInstance();
		try {
			Class.forName(dbc.getDriver());
			conn = (Connection) DriverManager.getConnection(dbc.getUrl(),dbc.getUsername(),dbc.getPassword());
			stmt = (Statement) conn.createStatement();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/* call the window for importing data */
		new ImportWindow(conn,stmt);
	}
}