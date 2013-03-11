package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class AddBookTest {

	@Test
	public void addBookTest() throws DaoException{
		
		BasicConfigurator.configure();
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		
		DaoCassandra dao = new DaoCassandra(cts);
		
		Book beggining_state = new Book();
		try {
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
			assertEquals(beggining_state.getId(), 1);
		} catch (Exception e) {throw new DaoException(e);}
		cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
	}
}
