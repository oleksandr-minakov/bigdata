package com.mirantis.aminakov.bigdatacourse;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DaoJdbcTest {
	static List<Book> books = null;
	static BookGenerator gen = null;
	
	@BeforeClass
	public static void testSetup() {
		ManagementTables mt;
		try {
			mt = new ManagementTables();
			mt.createTables();
			mt.closeConnection();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		books = new ArrayList<Book>();
    	gen = new BookGenerator();
    	books = gen.generateBooks();
	}
	
	@AfterClass
	public static void testCleanup() {
		ManagementTables mt;
		try {
			mt = new ManagementTables();
			mt.dropTables();
			mt.closeConnection();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		books.clear();
		books = null;
		gen = null;
	}
	
	@Test
	public void testDaoJdbc() {
		try {
			DaoJdbc dao = new DaoJdbc();
			assertNotNull(dao);
			assertNotNull(dao.con);	
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddBook() {
		try {
			int i = 5;
			DaoJdbc dao = new DaoJdbc();
			for (Book book: books) {
				dao.addBook(book);
			}
			List<Book> actual = new ArrayList<Book>();
			actual = dao.getBookByTitle(1, 10, books.get(i).getTitle());
			assertEquals(books.get(i).getTitle(), actual.get(0).getTitle());
			assertEquals(books.get(i).getAuthor(), actual.get(0).getAuthor());
			assertEquals(books.get(i).getGenre(), actual.get(0).getGenre());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test(expected = BookAlredyExists.class)
	public void testAddExistBook() {
		int i = 10;
		DaoJdbc dao;
		// TODO fix test
		try {
			dao = new DaoJdbc();
			dao.addBook(books.get(i));
			System.out.println(books.get(i).getTitle());
			System.out.println(books.get(i).getGenre());
			System.out.println(books.get(i).getAuthor());
			dao.addBook(books.get(i));
			System.out.println("After existsBook add.");
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllBooks() {
		try {
			DaoJdbc dao = new DaoJdbc();
			List<Book> getBooks = new ArrayList<Book>();
			getBooks = dao.getAllBooks(1, 50);
			int check = 0;
			int i = 0;
			for (Book book : getBooks) {
				if (book.getTitle() == books.get(i).getTitle()) {
					check = 0;
					i++;
				}
				check = -1;
			}
			assertEquals(-1, check);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetBookByAuthor() {
		int expectedAuthorCounter = 0;
		String author = gen.authors.get(5);
		for (Book book : gen.books) {
			if(book.getAuthor().equals(author))
				expectedAuthorCounter++;
		}
		List<Book> books = new ArrayList<Book>();
		DaoJdbc dao;
		try {
			dao = new DaoJdbc();
			books = dao.getBookByAuthor(1, 50, author);
			assertEquals(expectedAuthorCounter, books.size());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetBookByGenre() {
		int expectedGenreCounter = 0;
		String genre = gen.genres.get(3);
		for (Book book : gen.books) {
			if(book.getGenre().equals(genre))
				expectedGenreCounter++;
		}
		List<Book> books = new ArrayList<Book>();
		DaoJdbc dao;
		try {
			dao = new DaoJdbc();
			books = dao.getBookByGenre(1, 50, genre);
			assertEquals(expectedGenreCounter, books.size());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAuthorByGenre() {
		int expectedAuthorCounter = 0;
		String genre = gen.genres.get(3);
		for (Book book : gen.books) {
			if(book.getGenre().equals(genre))
				expectedAuthorCounter++;
		}
		TreeSet<String> authors = new TreeSet<String>();
		DaoJdbc dao;
		try {
			dao = new DaoJdbc();
			authors = dao.getAuthorByGenre(1, 50, genre);
			assertEquals(expectedAuthorCounter, authors.size());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testGetBookByText() {
		int expectedAuthorCounter = 0;
		InputStream text = gen.texts.get(5);
		
		// TODO fix test
		
		for (Book book : gen.books) {
			if(book.getText().equals(text))
				expectedAuthorCounter++;
		}
		TreeSet<String> authors = new TreeSet<String>();
		DaoJdbc dao;
		try {
			dao = new DaoJdbc();
			authors = dao.getAuthorByGenre(1, 50, "text");
			assertEquals(expectedAuthorCounter, authors.size());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
