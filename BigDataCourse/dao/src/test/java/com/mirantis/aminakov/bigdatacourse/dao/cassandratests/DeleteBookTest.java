package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import java.io.FileInputStream;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class DeleteBookTest {

	@Test
	public void deleteBookTest() throws DaoException{
		
		BasicConfigurator.configure();

		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		
		DaoCassandra dao = new DaoCassandra(cts);
		
		Book beggining_state = new Book();
		
		try {
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
			dao.delBook(117);
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
}
