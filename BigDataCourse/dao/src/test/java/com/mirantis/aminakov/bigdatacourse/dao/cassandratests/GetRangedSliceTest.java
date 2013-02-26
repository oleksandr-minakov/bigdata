package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoApp;

public class GetRangedSliceTest {

	@SuppressWarnings("unused")
	@Test
	public void getRangedSlicesTest() throws DaoException{
		
		BasicConfigurator.configure();
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		List<Book> after1 = new ArrayList<Book>();
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		DaoApp dao = new DaoApp(cts);
		
		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 40; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				dao.addBook(beggining_state);
			}
			after = dao.getAllBooks(1, 40);
			after1 = dao.getAllBooks(2, 20);
			
			assertTrue((after.size() + after1.size()) == (40 + 20));
			
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (Exception e) {throw new DaoException(e);}
	}
}
