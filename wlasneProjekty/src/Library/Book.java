package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Book {
	Connection conn;
	Scanner scanner;
	String title;
	String author;
	String type;
	int liczbaEgzemplarzy;
	String addedBy;
	
	boolean isNumber = false;
	User loggedIn;
	
	
	public Book( String title, String author, String type, int liczbaEgzemplarzy, String addedBy) {
	
		this.title = title;
		this.author= author;
		this.type=type;
		this.liczbaEgzemplarzy=liczbaEgzemplarzy;
		this.addedBy=addedBy;
	}
	
	public Book(Scanner scanner, Connection conn) {
		this.scanner = scanner;
		this.conn= conn;
	}
	
	String displayInfo() {
		return this.title+ " "+this.author+" "+ this.type +" " +this.liczbaEgzemplarzy;
	}
	
	
	
	String getTitle() {
		return this.title;
	}
	
	String getAuthor() {
		return this.author;
	}
	
	String getGenre() {
		return this.type;
	}
	
	int getQuantity() {
		return this.liczbaEgzemplarzy;
	}
	
	String getAddedBy() {
		return this.addedBy = addedBy;
	}
	
	
	
	public void addBook(Scanner scanner, User loggedIn) {
		
		String dodajacy = (loggedIn != null) ? loggedIn.userName : "anonymous";
		
		System.out.println("Podaj tytuł ksiazki którą chciłbys oddac: ");
		String title = scanner.nextLine();
		System.out.println("Podaj dziedzine ksiazki którą chciłbys oddac: ");
		String type = scanner.nextLine();
		System.out.println("Podaj autora ksiazki którą chciłbys oddac: ");
		String author = scanner.nextLine();
	
		while(!isNumber) {
			System.out.println("Podaj liczbe egzemplarzy ksiazki którą chciłbys oddac: ");
			if(scanner.hasNextInt()) {
				
				liczbaEgzemplarzy = scanner.nextInt();
				if(liczbaEgzemplarzy<=0) {
					System.out.println("LICZBA NIE MOZE BYC UJEMNA");
					scanner.nextLine();
				}else {
				scanner.nextLine();
				isNumber = true;
				}
			
			}else {
				System.out.println("PODAJ LICZBE A NIE INNE ZNAKI");
				scanner.nextLine();//T CZYSCI ZLY OUTPUT
			}
		}
		
		Book book = new Book(title, author, type, liczbaEgzemplarzy, dodajacy);
		
		String insertSQL = "INSERT INTO Book (Title, Author, Genre, Quantity, AddedBy) VALUES (?, ?, ?, ?, ?)";
		
		String url = "jdbc:sqlserver://localhost:1433;databaseName=library;encrypt=true;trustServerCertificate=true;";
		String user = "prycerz";
		String password = "Tatami05$";
		
		try (Connection conn = DriverManager.getConnection(url, user, password);
		     PreparedStatement ps = conn.prepareStatement(insertSQL)) {

		
		        
		        ps.setString(1, book.getTitle());
		        ps.setString(2, book.getAuthor());
		        ps.setString(3, book.getGenre());
		        ps.setInt(4, book.getQuantity());
		        ps.setString(5, book.getAddedBy());
		        ps.executeUpdate();
		    

		    System.out.println("Książki zapisane do bazy danych!");

		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		
	}

	public void lendBook(User loggedIn, Scanner scanner, Connection conn) {
	    String wypozyczajacy = (loggedIn != null) ? loggedIn.userName : "anonymous";
		
	    System.out.println("Podaj tytuł książki, którą chcesz wypożyczyć:");
	    String title = scanner.nextLine();

	    String selectSql = "SELECT BookId, Title, Genre, Quantity FROM Book WHERE Title = ?";
	    try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
	        ps.setString(1, title);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int bookId = rs.getInt("BookId");
	            String foundTitle = rs.getString("Title");
	            int quantity = rs.getInt("Quantity");
	            String genre = rs.getString("Genre");

	            if (quantity > 0) {
	             
	                String updateSql = "UPDATE Book SET Quantity = Quantity - 1 WHERE BookId = ?";
	                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
	                    updatePs.setInt(1, bookId);
	                    updatePs.executeUpdate();
	                }

	                String insertSql = "INSERT INTO Lended (BookId, UserName, Title, Genre) VALUES (?, ?, ?, ?)";
	                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
	                    insertPs.setInt(1, bookId);
	                    insertPs.setString(2, wypozyczajacy);
	                    insertPs.setString(3, foundTitle);
	                    insertPs.setString(4, genre);
	                    insertPs.executeUpdate();
	                }

	                System.out.println("Wypożyczono książkę: " + foundTitle);
	            } else {
	                System.out.println("Brak dostępnych egzemplarzy tej książki.");
	            }
	        } else {
	            System.out.println("Nie znaleziono książki o podanym tytule.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public void zwrocKsiazke(User loggedIn, Scanner scanner, Connection conn) {
	    String oddajacy = (loggedIn != null) ? loggedIn.userName : "anonymous";
	    System.out.println("Użytkownik: " + oddajacy);

	    try {
	        String selectLended = "SELECT L.Id, L.BookId, B.Title, B.Author " +
	                              "FROM Lended L " +
	                              "JOIN Book B ON L.BookId = B.BookId " +
	                              "WHERE L.UserName = ?";
	        try (PreparedStatement ps = conn.prepareStatement(selectLended)) {
	            ps.setString(1, oddajacy);
	            ResultSet rs = ps.executeQuery();

	            if (!rs.isBeforeFirst()) {
	                System.out.println("Nie masz żadnych wypożyczonych książek do oddania.");
	                return;
	            }

	            System.out.println("Twoje wypożyczone książki:");
	            while (rs.next()) {
	                int lendedId = rs.getInt("Id");
	                int bookId = rs.getInt("BookId");
	                String title = rs.getString("Title");
	                String author = rs.getString("Author");
	                System.out.println("Lended ID: " + lendedId + ", Tytuł: " + title + ", Autor: " + author);
	            }
	        }

	        System.out.println("Podaj tytuł książki do zwrotu:");
	        String titleToReturn = scanner.nextLine();

	        String selectBookId = "SELECT BookId FROM Book WHERE Title = ?";
	        int bookId = -1;
	        try (PreparedStatement ps = conn.prepareStatement(selectBookId)) {
	            ps.setString(1, titleToReturn);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                bookId = rs.getInt("BookId");
	            } else {
	                System.out.println("Nie znaleziono książki o podanym tytule.");
	                return;
	            }
	        }

	        String deleteLended = "DELETE FROM Lended WHERE BookId = ? AND UserName = ?";
	        try (PreparedStatement ps = conn.prepareStatement(deleteLended)) {
	            ps.setInt(1, bookId);
	            ps.setString(2, oddajacy);
	            int rows = ps.executeUpdate();
	            if (rows > 0) {
	                System.out.println("Książka została zwrócona.");

	                String updateBook = "UPDATE Book SET Quantity = Quantity + 1 WHERE BookId = ?";
	                try (PreparedStatement ps2 = conn.prepareStatement(updateBook)) {
	                    ps2.setInt(1, bookId);
	                    ps2.executeUpdate();
	                }
	            } else {
	                System.out.println("Nie udało się zwrócić książki.");
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	
	public void deleteBook(User loggedIn, Scanner scanner, Connection conn) {
	    String dodajacy = (loggedIn != null) ? loggedIn.userName : "anonymous";

	    System.out.println("PODAJ TYTUL KSIĄŻKI, KTÓRĄ CHCESZ USUNĄĆ:");
	    String title = scanner.nextLine();

	    String sql = "SELECT BookId, Title, Author, Genre, AddedBy FROM Book WHERE Title = ? AND AddedBy = ?";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, title);
	        ps.setString(2, dodajacy);
	        ResultSet rs = ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {
	            int bookId = rs.getInt("BookId");
	            String foundTitle = rs.getString("Title");
	            String author = rs.getString("Author");
	            String genre = rs.getString("Genre");
	            String addedBy = rs.getString("AddedBy");

	            found = true;

	            String checkLended = "SELECT COUNT(*) FROM Lended WHERE BookId = ?";
	            try (PreparedStatement psLended = conn.prepareStatement(checkLended)) {
	                psLended.setInt(1, bookId);
	                ResultSet rsLended = psLended.executeQuery();
	                rsLended.next();
	                int count = rsLended.getInt(1);

	                if (count > 0) {
	                    System.out.println("Nie możesz usunąć tej książki dopóki wszyscy jej wypożyczający nie zwrócą egzemplarzy.");
	                    return;
	                }
	            }

	            System.out.println("Znaleziono książkę:");
	            System.out.println("Tytuł: " + foundTitle + ", Autor: " + author + ", Genre: " + genre + ", AddedBy: " + addedBy);
	            System.out.println("Czy chcesz ją usunąć? (tak/nie)");

	            String decyzja = scanner.nextLine();
	            if (decyzja.equalsIgnoreCase("tak")) {
	                String deleteSql = "DELETE FROM Book WHERE BookId = ?";
	                try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
	                    deletePs.setInt(1, bookId);
	                    int rows = deletePs.executeUpdate();
	                    if (rows > 0) {
	                        System.out.println("Książka została usunięta.");
	                    } else {
	                        System.out.println("Nie udało się usunąć książki.");
	                    }
	                }
	            } else {
	                System.out.println("Anulowano usuwanie.");
	            }
	        }

	        if (!found) {
	            System.out.println("Nie znaleziono książki o podanym tytule dodanej przez użytkownika: " + dodajacy);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	
	public void listOfLendedBooks(Connection conn, User loggedIn) {

	    String oddajacy = (loggedIn != null) ? loggedIn.userName : "anonymous";
	    System.out.println("Użytkownik: " + oddajacy);

	    String selectLended = "SELECT L.Id, L.BookId, B.Title, B.Author " +
	                          "FROM Lended L " +
	                          "JOIN Book B ON L.BookId = B.BookId " +
	                          "WHERE L.UserName = ?";

	    try (PreparedStatement ps = conn.prepareStatement(selectLended)) {
	        ps.setString(1, oddajacy);
	        try (ResultSet rs = ps.executeQuery()) {

	            if (!rs.isBeforeFirst()) { 
	                System.out.println("Nie masz żadnych wypożyczonych książek.");
	                return;
	            }

	            System.out.println("Twoje wypożyczone książki:");
	            while (rs.next()) {
	                int lendedId = rs.getInt("Id");
	                int bookId = rs.getInt("BookId");
	                String title = rs.getString("Title");
	                String author = rs.getString("Author");

	                System.out.println("Lended ID: " + lendedId + ", Tytuł: " + title + ", Autor: " + author);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Wystąpił błąd podczas pobierania wypożyczonych książek.");
	        e.printStackTrace();
	    }
	}

	public void bookOfUser(Scanner scanner, Connection conn) {
	    System.out.println("Podaj nazwę użytkownika, którego książki chcesz wyświetlić:");
	    String username = scanner.nextLine();

	    String sql = "SELECT Title, Author, Genre, Quantity FROM Book WHERE AddedBy = ?";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {
	            found = true;
	            String title = rs.getString("Title");
	            String author = rs.getString("Author");
	            String genre = rs.getString("Genre");
	            int quantity = rs.getInt("Quantity");

	            System.out.println("Tytuł: " + title + ", Autor: " + author + ", Gatunek: " + genre + ", Liczba egzemplarzy: " + quantity);
	        }

	        if (!found) {
	            System.out.println("Brak książek dodanych przez użytkownika: " + username);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	

}
