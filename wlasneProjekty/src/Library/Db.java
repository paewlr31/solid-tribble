package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Db {

	String url;
	String user;
	String password;
	
	//lll
	
	
	
	ArrayList<Book> array;
	Book book;
	
	public Db(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
	


	public Connection dbConnect() {
		

		


	        try  {
	        	Connection conn = DriverManager.getConnection(url, user, password);
	             Statement stmt = conn.createStatement();
	            System.out.println("Połączono z bazą!");

	            ResultSet rs = stmt.executeQuery("SELECT name FROM sys.databases");
	            while (rs.next()) {
	                System.out.println("DB: " + rs.getString("name"));
	            }
	            	return conn;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	      
	}
	
	void insertBook(ArrayList<Book> array) {
		String insertSQL = "INSERT INTO Book (Title, Author, Genre, Quantity) VALUES (?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
		     PreparedStatement ps = conn.prepareStatement(insertSQL)) {

			for (Book b : array) {
		        
		        ps.setString(1, b.getTitle());
		        ps.setString(2, b.getAuthor());
		        ps.setString(3, b.getGenre());
		        ps.setInt(4, b.getQuantity());
		        ps.executeUpdate();
		    }

		    System.out.println("Książki zapisane do bazy danych!");

		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
	}
	
	void listFromDatabase() {
		
	}
	
	
	
}
