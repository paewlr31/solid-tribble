package Library;

import java.util.ArrayList;
import java.util.Scanner;

public class Library {
	String libraryName ;
	int rokZal ;
	Scanner scanner;
	
	 ArrayList<Book> array;
	
	public Library (String libraryName, int rokZal, ArrayList<Book> array ){
		this.libraryName = libraryName;
		this.rokZal= rokZal;
		this.array=array;
	
		//this.books = array;
	}
	
	String displayInfo() {
		
		return this.libraryName +" "+this.rokZal;
	}
	
	void listaKsiazek( ArrayList<Book> array) {
			for(Book book: array) {
			System.out.println(book.title+ " "+book.author+" "+ book.type +" " +book.liczbaEgzemplarzy);
		}
	}
	

	
	public void Welcome() {
		
		System.out.println("WITAMY W BIBLIOTECE. POWIEDZ CO CHCESZ ZROBIC");
		System.out.println("1 - WYPOZYCZ KSIAZKE ");
		System.out.println("2 - DAJ KSIAZKE DO BIBLIOTEKI ");
		System.out.println("3 - Zwracanie książek, KTORA WYPOZYCZYLEM ");
		System.out.println("4 - Usuwanie książek z biblioteki");
		System.out.println("5 - Wyszukiwanie książek po tytule ");
		System.out.println("6 - Wyszukiwanie książek autorze ");
		System.out.println("7 - Wyszukiwanie książek gatunku ");
		System.out.println("8 - Wyświetlanie wszystkich książek alfabetycznie ");
		System.out.println("9 - Wyświetlanie listy wypożyczonych książek dla użytkownika ");
	
		System.out.println("10 - Rejestrowanie użytkowników -zrobione");
		System.out.println("11 - Logowanie użytkowników - zrobione");
		System.out.println("12 - UCIEKAM");
		
		//TERAZ LOGOWANIE I REJESRACJA UZYTKOWNIKA
		
		//Prosty menedżer biblioteki – książki z tytułem, autorem, możliwością wypożyczenia.
		/*
		 * Dodawanie książek do biblioteki - dziala
		 * Usuwanie książek z biblioteki - jak bedzie logowanie
		 * Wyszukiwanie książek (po tytule, autorze, gatunku) - dziala wszystko (jak po tytule to wypsiuje sie wszystko i dostepnosc)
		 * Wyświetlanie wszystkich książek alfabetycznie - dziala
		 * teraz chyba zrobienie tej duzej petli
		 * Rejestrowanie użytkowników - do pliku txt
		 * Wypożyczanie książek użytkownikom
		 * Zwracanie książek
		 * Sprawdzanie dostępności książek
		 * Wyświetlanie listy wypożyczonych książek dla użytkownika
		 * Sortowanie książek (np. alfabetycznie lub po autorze)
		 */
	}
	

	
}
