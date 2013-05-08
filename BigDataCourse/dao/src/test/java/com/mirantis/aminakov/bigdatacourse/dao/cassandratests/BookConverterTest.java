package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.BookConverter;
import me.prettyprint.hector.api.beans.HColumn;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class BookConverterTest {

	public Book bstate;
	public Book fstate;
	public List<HColumn<String, String>> cols;
	
	public void setUp(){
		bstate = new Book();
		fstate = new Book();
		cols = new ArrayList<HColumn<String, String>>();
	}
	
	@Test
	public void book2rowTest() throws DaoException{
		try {
			setUp();
			bstate.newBook("CassandraTest", "Test", "Tester", new FileInputStream(BookPath.path));
			cols = BookConverter.getInstance().book2row(bstate);
			assertNotNull(cols);
		} catch (IOException e) {
			throw new DaoException(e);}
	}
	
	@Test
	public void row2bookTest() throws DaoException{
		setUp();
		try {
			bstate.setId(100);
			bstate.newBook("CassandraTest", "Test", "Tester", new FileInputStream(BookPath.path));
			cols = BookConverter.getInstance().book2row(bstate);
			fstate = BookConverter.getInstance().row2book(cols);
			assertNotNull(cols);
		} catch (IOException e) {
			throw new DaoException(e);
		}
		
	}
}
