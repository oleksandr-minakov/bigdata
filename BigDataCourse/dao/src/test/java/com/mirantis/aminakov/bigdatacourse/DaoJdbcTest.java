package com.mirantis.aminakov.bigdatacourse;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

//import org.junit.After;
import org.junit.AfterClass;
//import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DaoJdbcTest {
	static List<Book> books = null;
	static BookGenerator gen = null;
	static DaoJdbc dao = null;
	
	@BeforeClass
	public static void testSetup() {
		ManagementTables mt;
		try {
			dao = new DaoJdbc();
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
			dao.closeConnection();
			mt = new ManagementTables();
			mt.dropTables();
			mt.closeConnection();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		books.clear();
	}
	
	@Test
	public void testDaoJdbc() {
		assertNotNull(dao);
	}

	@Test
	public void testAddBook() throws DaoException {
		int i = 5;
		for (Book book: books) {
			dao.addBook(book);
		}
		List<Book> actual = new ArrayList<Book>();
		actual = dao.getBookByTitle(1, 10, books.get(i).getTitle());
		assertEquals(books.get(i).getTitle(), actual.get(0).getTitle());
		assertEquals(books.get(i).getAuthor(), actual.get(0).getAuthor());
		assertEquals(books.get(i).getGenre(), actual.get(0).getGenre());
	}
	
	@Test(expected = BookAlredyExists.class)
	public void testAddExistBook() throws DaoException {
		int i = 10;
			dao.addBook(books.get(i));
	}

	@Test
	public void testGetAllBooks() throws DaoException {
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
	}

	@Test
	public void testGetBookByAuthor() throws DaoException {
		int expectedAuthorCounter = 0;
		String author = gen.authors.get(5);
		for (Book book : gen.books) {
			if(book.getAuthor().equals(author))
				expectedAuthorCounter++;
		}
		List<Book> books = new ArrayList<Book>();
		books = dao.getBookByAuthor(1, 50, author);
		assertEquals(expectedAuthorCounter, books.size());
	}

	@Test
	public void testGetBookByGenre() throws DaoException {
		int expectedGenreCounter = 0;
		String genre = gen.genres.get(3);
		for (Book book : gen.books) {
			if(book.getGenre().equals(genre))
				expectedGenreCounter++;
		}
		List<Book> books = new ArrayList<Book>();
		books = dao.getBookByGenre(1, 50, genre);
		assertEquals(expectedGenreCounter, books.size());
	}

	@Test
	public void testGetAuthorByGenre() throws DaoException {
		HashSet<String> expectedAuthors = new HashSet<String>(); 
		String genre = gen.genres.get(3);
		for (Book book : gen.books) {
			if(book.getGenre().equals(genre))
				expectedAuthors.add(book.getAuthor());
		}
		TreeSet<String> authors = new TreeSet<String>();
		authors = dao.getAuthorByGenre(1, 50, genre);
		assertEquals(expectedAuthors.size(), authors.size());
	}
	
	@Ignore
	@Test
	public void testGetBookByText() throws DaoException {
		int expectedAuthorCounter = 0;
		InputStream text = gen.texts.get(5);
		
		for (Book book : gen.books) {
			if(book.getText().equals(text))
				expectedAuthorCounter++;
		}
		TreeSet<String> authors = new TreeSet<String>();
			authors = dao.getAuthorByGenre(1, 50, "text");  // TODO implement get text from DB to file 
			assertEquals(expectedAuthorCounter, authors.size());
	}

}
