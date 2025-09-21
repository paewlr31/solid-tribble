package Library;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class Main {
	

	
	public static void main(String[] args) throws SQLException, IOException {
		
		/*
		 * HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		 * 
		 * server.createContext("/", new HttpHandler() {
		 * 
		 * @Override public void handle(HttpExchange exchange) throws IOException {
		 * String response = "Hello, World!"; exchange.sendResponseHeaders(200,
		 * response.getBytes().length); OutputStream os = exchange.getResponseBody();
		 * os.write(response.getBytes()); os.close(); } });
		 * 
		 * server.start();
		 * System.out.println("Server started on http://localhost:8080"); try {
		 * Thread.sleep(500); } catch (InterruptedException e) {}
		 */
		String url = "jdbc:sqlserver://localhost:1433;databaseName=library;encrypt=true;trustServerCertificate=true;";
		String user = "prycerz";
		String password = "Tatami05$";
		Db db = new Db(url, user, password);
		Connection conn = db.dbConnect();
		//Statement stmt = conn.createStatement();
		Scanner scanner = new Scanner(System.in);
		Sort sorter = new Sort(conn, scanner);
		Book book = new Book(scanner, conn);
		
		User userLibrary = new User();
		User loggedIn = null;
		
			
		ArrayList<Book> array= new ArrayList<>();
		String libraryName = "THE GREAT LIBRARY";
		int rokZal = 1892;
		int liczbaEgzemplarzy = 0;
		boolean isNumber = false;
		String input = "w";
		
		boolean valid = false;
		
		
		
		//int bookId, String title, String author, String type
		Library library = new Library(libraryName, rokZal, array);
		library.Welcome();
		
		library.listaKsiazek(array);
		System.out.println(library.displayInfo());
		/*
		 * System.out.println(book1.displayInfo());
		 * System.out.println(book2.displayInfo());
		 */
		System.out.println("Wybierz opcje lub kliknij q, jeśli chcesz uciec");

		while (!valid) {
		    input = scanner.nextLine();

		    if (input.length() > 0) {
		        if (input.charAt(0) == 'q') {
		            break;
		        }

		        try {
		            int opcja = Integer.parseInt(input);

		            if (opcja > 0 && opcja <= 13) {
		                System.out.println("HEEYY");
		                switch (opcja) {
		                    case 1 -> {
		                        if (loggedIn != null) {
		                            book.lendBook(loggedIn, scanner, conn);
		                        } else {
		                            System.out.println("MUSISZ SIĘ NAJPIERW ZALOGOWAĆ, ŻEBY WYPOŻYCZYĆ KSIĄŻKI");
		                        }
		                    }
		                    case 2 -> book.addBook(scanner, loggedIn);
		                    case 3 -> {
		                        if (loggedIn != null) {
		                            book.zwrocKsiazke(loggedIn, scanner, conn);
		                        } else {
		                            System.out.println("MUSISZ SIĘ NAJPIERW ZALOGOWAĆ, ŻEBY ZWRÓCIĆ KSIĄŻKĘ");
		                        }
		                    }
		                    case 4 -> {
		                        if (loggedIn != null) {
		                            book.deleteBook(loggedIn, scanner, conn);
		                        } else {
		                            System.out.println("MUSISZ SIĘ NAJPIERW ZALOGOWAĆ, ŻEBY MÓC USUNĄĆ SWOJE KSIĄŻKI");
		                        }
		                    }
		                    case 5 -> sorter.sortTitleFromDB(conn, scanner);
		                    case 6 -> sorter.sortAuthorFromDB(conn, scanner);
		                    case 7 -> sorter.sortTypeFromDB(conn, scanner);
		                    case 8 -> sorter.sortOutFromDB(conn, scanner);
		                    case 9 -> {
		                        if (loggedIn != null) {
		                            book.listOfLendedBooks(conn, loggedIn);
		                        } else {
		                            System.out.println("MUSISZ SIĘ NAJPIERW ZALOGOWAĆ, ŻEBY MÓC WYPOŻYCZAĆ KSIĄŻKI DLA DANEGO UŻYTKOWNIKA");
		                        }
		                    }
		                    case 10 -> userLibrary.register(scanner, conn);
		                    case 11 -> {
		                        loggedIn = userLibrary.login(scanner, conn);
		                        break;
		                    }
		                    case 12 -> System.out.println("UCIEKAM");
		                    case 13 -> book.bookOfUser(scanner, conn);
		                }

		                if (loggedIn != null) {
		                    System.out.println("ZALOGOWANY JAKO " + loggedIn.userName);
		                } else {
		                    System.out.println("NIE ZALOGOWANY");
		                }
		                System.out.println("Wybierz opcje lub kliknij q, jeśli chcesz uciec");

		            } else {
		                System.out.println("Liczby powinny być między 1 a 13");
		            }

		        } catch (NumberFormatException e) {
		            System.out.println("To nie jest liczba! Wpisz poprawną opcję od 1 do 13 lub 'q', aby wyjść.");
		        }
		    } else {
		        System.out.println("Nie wpisałeś żadnej opcji!");
		    }
		}

		

		
		
		
		
		
		scanner.close();
		
	}
	
	
}
