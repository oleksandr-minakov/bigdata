package com.mirantis.aminakov.bigdatacourse;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DaoJdbcTest {
	static List<Book> books = null;
	static ManagementBooks gen = null;
	static DaoJdbc dao = null;
	
	@BeforeClass
	public static void testSetup() throws DaoException {
		ManagementTables mt;
		dao = new DaoJdbc();
		mt = new ManagementTables();
		mt.createTables();
		mt.closeConnection();
		books = new ArrayList<Book>();
    	gen = new ManagementBooks();
    	books = gen.generateBooks();
	}
	
	@AfterClass
	public static void testCleanup() throws DaoException {
		ManagementTables mt;
		dao.closeConnection();
		mt = new ManagementTables();
		mt.dropTables();
		mt.closeConnection();
		books.clear();
		gen.deleteFiles();
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
	
	@Test
	public void testGetBookByText() throws DaoException, IOException {
		int expected = 0;
		List<Book> resultBooks = new ArrayList<Book>();
        File fileExpected = new File("file10.txt");
        InputStream fis = new FileInputStream(fileExpected);
		byte[] buf = new byte[10];
		fis.read(buf, 0, 10);
		String text = new String(buf);
		resultBooks = dao.getBookByText(1, 50, text);
		fis.close();
		fis = new FileInputStream(fileExpected);
		for (int i = 0; i < resultBooks.get(0).getText().available(); i++) {
			if (fis.read() == resultBooks.get(0).getText().read()) {
				expected = 1;
			} else {
				expected = 0;
			}
		}
		fis.close();
		assertEquals(expected, 1);
	}
}
