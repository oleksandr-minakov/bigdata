package com.mirantis.aminakov.bigdatacourse;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.Test;

public class DaoJdbcTest {

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
		FileInputStream fis = null;
	    File file = new File("/home/aminakov/test4.txt");
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try {
			DaoJdbc dao = new DaoJdbc();
			Book book = new Book();
			assertNotNull(dao);
			assertNotNull(book);
			book.setAuthor("Сидоров А.А.");
			book.setGenre("Хокку");
			book.setTitle("Первый стих");
			book.setText(fis);
			dao.addBook(book);
			/*dao.rs = null;
			dao.st = null;
			dao.pst = null;
			dao.addBook(book);*/
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllBooks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBookByTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBookByAuthor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBookByGenre() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAuthorByGenre() {
		fail("Not yet implemented");
	}

}
