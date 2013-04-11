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

public class DeleteBookTest {

	@Test
	public void deleteBookTest() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		
		Book beggining_state = new Book();
		
		try {
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			
			dao.addBook(beggining_state);
			
			int res = dao.delBook(beggining_state.getId());
			
			System.out.println( res == 0 );
			
			assertTrue(res == 0);
			
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (Exception e) {throw new DaoException(e);}
	}
	
}
