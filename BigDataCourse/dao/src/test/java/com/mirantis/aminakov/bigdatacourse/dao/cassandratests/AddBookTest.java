package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddBookTest {

	@Test
	public void addBookTest() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		Book initial_state = new Book();
		try {
			initial_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("testbook"));
			dao.addBook(initial_state);
			System.out.println(initial_state.getId() == cts.bookID - 1);
			assertEquals(initial_state.getId(), cts.bookID - 1);
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        }
	}
}
