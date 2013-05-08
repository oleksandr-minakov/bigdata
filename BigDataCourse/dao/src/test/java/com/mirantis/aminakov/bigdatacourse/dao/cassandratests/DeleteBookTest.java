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

import static org.junit.Assert.assertTrue;

public class DeleteBookTest {

	@Test
	public void deleteBookTest() throws DaoException, InterruptedException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP2);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		Book initial_state = new Book();
		try {
			initial_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream(BookPath.path));
			dao.addBook(initial_state);
			int res = dao.delBook(initial_state.getId());
			System.out.println( res == 0 );
			assertTrue(res == 0);
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        }
	}
}
