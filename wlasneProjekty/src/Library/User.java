package Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User {
	
	String userName;
	String userPassword;
	String repeatPassword;
	String userMail;
	
	Scanner scanner;
	
	Connection conn;

	
	public User(String userName, String userPassword, String userMail, String repeatPassword) {
		this.userName=userName;
		this.userMail=userMail;
		this.userPassword=userPassword;
		this.repeatPassword=repeatPassword;
	}
	
	public User() {
		
	}
	
	public User(String userName, String userPassword) {
		this.userName=userName;
		
		this.userPassword=userPassword;
		
	}
	
	public User(String userName, String userPassword, String userMail) {
		this.userName=userName;
		this.userMail=userMail;
		this.userPassword=userPassword;
	}
	
	String getUserName() {
		return this.userName;
	}
	
	String getUserPassword() {
		return this.userPassword;
	}
	
	String getRepeatUserPassword() {
		return this.repeatPassword;
	}
	
	String getUserMail() {
		return this.userMail;
	}
	
	
	
	
	User login(Scanner scanner, Connection conn) throws SQLException {
		while(true) {
		System.out.println("Podaj nazwe uzytkownika");
		String userName = scanner.nextLine();
		
		System.out.println("Podaj haslo");
		String userPassword = scanner.nextLine();
		
	
		
		String checkUserSQL = "SELECT UserName, UserPassword FROM [User] WHERE RTRIM(UserName) = ? AND RTRIM(UserPassword) = ?";
		try (PreparedStatement checkUser = conn.prepareStatement(checkUserSQL)) {
			checkUser.setString(1, userName);
			checkUser.setString(2, userPassword);
		    ResultSet rs = checkUser.executeQuery();
		    if (rs.next()) {
		        System.out.println("Witaj " + userName + " !");
		        User user = new User(userName, userPassword);
		        //System.out.println(user.userName);
		        return new User(userName, userPassword);
		  
		    }else {
		    	System.out.println("Bledne dane logowania");
		    }
		
		}
	}
	
	}
	
	

	
	void register(Scanner scanner, Connection conn) throws SQLException {
		
		System.out.println("Podaj nazwe uzytkownika");
		String userName = scanner.nextLine();
		
		System.out.println("Podaj haslo");
		String userPassword = scanner.nextLine();
		
		while(userPassword.length()<8 || userPassword.matches(".*\\s+.*")) {
			System.out.println("Podaj haslo jescze raz, musibyc dluzsze niz osiem znakow ani puste");
			userPassword = scanner.nextLine();
		}
	
		
		
		boolean isEqual = false;
		int licznikBledow = 0;
		
		while(!isEqual) {
			System.out.println("Powtorz haslo");
			if(licznikBledow == 0) {
				String repeatPassword = scanner.nextLine();
				if(repeatPassword.equals(userPassword)) {
				isEqual = true;
				licznikBledow++;
				}else {
					System.out.println("To haslo nie jest takie samo jak pierwsze. Powtorz haslo jescze raz");
				
					continue;
				}
		}
		
		
	
		 String userMail = null;
		boolean isValid = false;
		
		int zwalidowanyMail = 0;
		while(!isValid)
		if(zwalidowanyMail==0) {
			System.out.println("Podaj maila");
			userMail = scanner.nextLine();
			
			String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
			        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(regexPattern);
			Matcher matcher = pattern.matcher(userMail);
			boolean isValidated = matcher.matches();
			if(isValidated) {
				System.out.println("Dobry mail");
				zwalidowanyMail++;
				isValid = true;
			}else {
			System.out.println("Podaj maila jescze raz");
			}
		}
		
		User user = new User(userName, userPassword, userMail);
		
		String checkSQL = "SELECT COUNT(*) FROM [User] WHERE UserMail = ?";
		try (PreparedStatement checkPs = conn.prepareStatement(checkSQL)) {
		    checkPs.setString(1, user.getUserMail());
		    ResultSet rs = checkPs.executeQuery();
		    if (rs.next() && rs.getInt(1) > 0) {
		        System.out.println("Taki użytkownik już istnieje!");
		        return; 
		    }
		}
		
		String insertSQL = "INSERT INTO [User] (UserName, UserPassword, UserMail) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(insertSQL);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserPassword());
			ps.setString(3, user.getUserMail());
			ps.executeUpdate();
			
			System.out.println("Zarejestrowałem usera");
		}catch(SQLException e) {
			  e.printStackTrace();
		}
		}
	}
}
