package com.mirantis.aminakov.bigdatacourse.dao.mysql;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.BookAlreadyExists;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.DeleteException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@SuppressWarnings("deprecation")
public class DaoJdbcTest {
	
	static List<Book> books = null;
	static ManagementBooks gen = null;
	static DaoJdbc dao = null;
    static DataSource dataSource = null;

	@BeforeClass
	public static void testSetup() throws DaoException {
        ManagementTables mt;
        dataSource = new DriverManagerDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bigdata", "aminakov", "bigdata");
		dao = new DaoJdbc();
        dao.setDataSource(dataSource);
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

	@Test(expected = BookAlreadyExists.class)
	public void testAddExistBook() throws DaoException {
		int i = 10;
			dao.addBook(books.get(i));
	}

	@Test
	public void testGetAllBooks() throws DaoException {
		List<Book> getBooks = new ArrayList<Book>();
		getBooks = dao.getAllBooks(1, 50);
		assertEquals(books.size(), getBooks.size());
	}

	@Test
	public void testGetBookByAuthor() throws DaoException {
        System.out.println("================= testGetBookByAuthor ===================");
		int expectedAuthorCounter = 0;
		String author = gen.authors.get(5);
        System.out.println(author);
		for (Book book : gen.books) {
			if(book.getAuthor().equals(author)) {
                System.out.println(expectedAuthorCounter);
                expectedAuthorCounter++;
                System.out.println(expectedAuthorCounter);
            }
		}
		List<Book> books = new ArrayList<Book>();
		books = dao.getBookByAuthor(1, 50, author);
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println(books.size());
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
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        List<Book> books = new ArrayList<Book>();
        int id = dao.addBook(book);
        try {
            books = dao.getBookByText(1, 1, "hisdu");
            String str1 = books.get(0).getReadableText();
            String str2 = book.getReadableText();
            assertTrue(str1.equals(str2));
        } finally {
            dao.delBook(id);
        }
	}

	@Test
	public void testDelBook() throws DaoException, FileNotFoundException {
        Book book = new Book();
        book.newBook("del", "del", "del", new FileInputStream("testbook"));
        int id = dao.addBook(book);
        int result = dao.delBook(id);
		assertEquals(0, result);
	}

	@Test(expected = DeleteException.class)
	public void testDelBookException() throws DaoException {
		dao.delBook(10);
		dao.delBook(10);
	}
}
