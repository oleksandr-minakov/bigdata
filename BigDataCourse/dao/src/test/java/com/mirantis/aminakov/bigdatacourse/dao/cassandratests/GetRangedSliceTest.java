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

public class GetRangedSliceTest {

	@Test
	public void getRangedSlicesTest() throws DaoException{

		List<Book> after = new ArrayList<Book>();
		List<Book> after1 = new ArrayList<Book>();
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		Book initial_state = new Book();
		try {
			for(int i = 0; i < 40; ++i){
				initial_state.newBook("CassandraTest" + String.valueOf(i),
                                        "Test" + String.valueOf(i),
                                        "Tester" + String.valueOf(i),
                                        new FileInputStream("testbook"));
				dao.addBook(initial_state);
			}
			after = dao.getAllBooks(1, 40);
			after1 = dao.getAllBooks(2, 20);
			System.out.println((after.size() + after1.size()) == (40 + 20));
			assertTrue((after.size() + after1.size()) == (40 + 20));
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        }
	}
}
