package com.mirantis.bigdatacourse.dao.mysql;

import com.mirantis.bigdatacourse.dao.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@SuppressWarnings("deprecation")
public class DaoJdbcTest {
	
	static List<Book> books = null;
	static ManagementBooks gen = null;
	static DaoJdbc dao = null;
    static DataSource dataSource = null;
    static PaginationModel model = null;

	@BeforeClass
	public static void testSetup() throws DaoException {
        ManagementTables mt;
        dataSource = new DriverManagerDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bigdata", "aminakov", "bigdata");
		dao = new DaoJdbc();
        dao.setDataSource(dataSource);
		mt = new ManagementTables(dataSource);
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
        mt = new ManagementTables(dataSource);
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
		model = dao.getBookByTitle(1, 10, books.get(i).getTitle());
        actual = model.getBooks();
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
		model = dao.getAllBooks(1, 50);
        getBooks = model.getBooks();
		assertEquals(books.size(), getBooks.size());
	}

	@Test
	public void testGetBookByAuthor() throws DaoException {
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
		model = dao.getBookByAuthor(1, 50, author);
        books = model.getBooks();
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
		model = dao.getBookByGenre(1, 50, genre);
		books = model.getBooks();
        assertEquals(expectedGenreCounter, books.size());
	}

	@Test
	public void testGetBookByText() throws DaoException, IOException {
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        List<Book> books = new ArrayList<Book>();
        dao.addBook(book);
        try {
            model = dao.getBookByText(1, 1, "hisdu");
            books = model.getBooks();
            String str1 = books.get(0).getReadableText();
            String str2 = book.getReadableText();
            assertTrue(str1.equals(str2));
        } finally {
            dao.delBook(book.getId());
        }
	}

	@Test
	public void testDelBook() throws DaoException, FileNotFoundException {
        Book book = new Book();
        book.newBook("del", "del", "del", new FileInputStream("testbook"));
        dao.addBook(book);
        int result = dao.delBook(book.getId());
		assertEquals(0, result);
	}

	@Test(expected = DeleteException.class)
	public void testDelBookException() throws DaoException {
		dao.delBook("10");
		dao.delBook("10");
	}
}
