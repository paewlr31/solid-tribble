package Library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.sql.*;

public class Sort {
	
	ArrayList<Book> array;
	Scanner scanner;
	Connection conn;

	
	public Sort(Connection conn, Scanner scanner) {
		this.conn=conn;
		this.scanner = scanner;
	
	}
	
	
	public Sort(ArrayList<Book> array, Scanner scanner){
			this.array=array;
			this.scanner = scanner;
	}
	
	void sortAuthor(ArrayList<Book> array) {
		
		System.out.println("WYSZUKAJ KSIAZKI PO AUTORZE");
		String author = scanner.nextLine();
		System.out.println("KSIAZKI "+author+ ": ");
		int i=0;
		for(Book books : array) {
			if(books.author.equals(author)) {
				System.out.println(books.title);
				i++;
			}else {
				continue;
			}
		}
		if(i==0) {
			System.out.println("BRAK KSIAZEK DLA TEGO AUTORA");
			
		}
	}
	
	

	public void sortAuthorFromDB(Connection conn, Scanner scanner) {
	   
	    System.out.println("WYSZUKAJ KSIAZKI PO AUTORZE:");
	    String author = scanner.nextLine();

	    String sql = "SELECT title, quantity FROM book WHERE author = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, author); // wstawiamy autora do zapytania
	        ResultSet rs = stmt.executeQuery();

	        int count = 0;
	        System.out.println("KSIAZKI " + author + ":");
	        while (rs.next()) {
	            String title = rs.getString("title");
	            int quantity = rs.getInt("quantity");
	            System.out.println("TITLE: " + title+" QUANTITY: "+quantity);
	            count++;
	        }

	        if (count == 0) {
	            System.out.println("BRAK KSIAZEK DLA TEGO AUTORA");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	//po gatunku i tytule

	public void sortTypeFromDB(Connection conn, Scanner scanner) {
		   
	    System.out.println("WYSZUKAJ KSIAZKI PO RODZAJU:");
	    String genre = scanner.nextLine();

	    String sql = "SELECT title, quantity FROM book WHERE genre = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, genre); // wstawiamy autora do zapytania
	        ResultSet rs = stmt.executeQuery();

	        int count = 0;
	        System.out.println("KSIAZKI " + genre + ":");
	        while (rs.next()) {
	            String title = rs.getString("title");
	            int quantity = rs.getInt("quantity");
	            System.out.println("TITLE: " + title+" QUANTITY: "+quantity);
	            count++;
	        }

	        if (count == 0) {
	            System.out.println("BRAK KSIAZEK DLA TEGO AUTORA");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void sortTitleFromDB(Connection conn, Scanner scanner) {
		   
	    System.out.println("WYSZUKAJ KSIAZKI PO TYTULE ABY ZOBACZYC JEJ DANE:");
	    String title = scanner.nextLine();

	    String sql = "SELECT title, author, genre, quantity FROM book WHERE title = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, title); // wstawiamy autora do zapytania
	        ResultSet rs = stmt.executeQuery();

	        int count = 0;
	        System.out.println("KSIAZKI " + title + ":");
	        while (rs.next()) {
	            title = rs.getString("title");
	            String author = rs.getString("author");
	            String genre = rs.getString("genre");
	            int quantity = rs.getInt("quantity");
	            System.out.println("TITLE: " + title+ " AUTHOR: "+ author +" GENRE: "+genre+" QUANTITY: "+quantity );
	            count++;
	        }

	        if (count == 0) {
	            System.out.println("BRAK KSIAZEK DLA TEGO TYTUŁU");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void sortOutFromDB(Connection conn, Scanner scanner) {
		   
	    System.out.println("WSZYSTKIE KSIAZKI DOSTEPNE W BIBLIOTECE");
	 

	    String sql = "SELECT title, author, genre, quantity FROM book ORDER BY title";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	       
	        ResultSet rs = stmt.executeQuery();

	        int count = 0;
	        System.out.println("KSIAZKI: ");
	        while (rs.next()) {
	            String title = rs.getString("title");
	            String author = rs.getString("author");
	            String genre = rs.getString("genre");
	            int quantity = rs.getInt("quantity");
	            count++;
	            System.out.println(count+ ". TITLE: " + title+ " AUTHOR: "+ author +" GENRE: "+genre+" QUANTITY: "+quantity );
	          
	           
	        }
	        
	     
	        

	        if (count == 0) {
	            System.out.println("BRAK KSIAZEK DLA TEGO TYTUŁU");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	




}
