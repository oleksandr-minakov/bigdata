package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class AddBookTest {

	@Test
	public void addBookTest() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		
		DaoCassandra dao = new DaoCassandra(cts);
		
		Book beggining_state = new Book();
		try {
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
			
			System.out.println(beggining_state.getId() == cts.bookID-1);
			
			assertEquals(beggining_state.getId(), cts.bookID-1);
			
		} catch (Exception e) {throw new DaoException(e);}
		
	}
}
