package com.mirantis.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.beans.HColumn;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.BookConverter;

public class BookConvTest {

	public Book bstate;
	public Book fstate;
	public List<HColumn<String, String>> cols;
	
	public void setUp(){
		
		bstate = new Book();
		fstate = new Book();
		cols = new ArrayList<HColumn<String, String>>();
	}
	
	@Test
	public void book2rowTest() throws DaoException {
			
		try {
			setUp();
			bstate.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			cols = BookConverter.getInstance().book2row(bstate);
			assertNotNull(cols);
			
		} catch (Exception e) {
			throw new DaoException(e);}
	}
	
	@Test
	public void row2bookTest() throws DaoException {
		
		setUp();
		try {
			bstate.setId(String.valueOf(100));
			bstate.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			cols = BookConverter.getInstance().book2row(bstate);
			fstate = BookConverter.getInstance().row2book(cols);
			assertNotNull(cols);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
		
		
		
		
	}
}
