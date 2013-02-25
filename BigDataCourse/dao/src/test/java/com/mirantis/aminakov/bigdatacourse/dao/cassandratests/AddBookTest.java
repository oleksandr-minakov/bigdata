package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DAOApp;

public class AddBookTest {

	@Test
	public void addBookTest(){
		
		BasicConfigurator.configure();
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		
		DAOApp dao = new DAOApp(cts);
		
		Book beggining_state = new Book();
		try {
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
			assertEquals(beggining_state.getId(), 1);
			
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (FileNotFoundException | DaoException e) {
				e.printStackTrace();
		}
	}
}
