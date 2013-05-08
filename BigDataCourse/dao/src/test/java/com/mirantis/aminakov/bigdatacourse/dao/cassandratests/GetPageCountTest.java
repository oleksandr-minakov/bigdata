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

public class GetPageCountTest {

    @Test
	public void getPageCountTest() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		List<String> books = new ArrayList<String>();
		Book initial_state = new Book();
		try {
			for(int i = 0; i < 100; ++i){
				
				initial_state.newBook("CassandraTest" + String.valueOf(i),
                                        "Test" + String.valueOf(i),
                                        "Tester" + String.valueOf(i),
                                        new FileInputStream(BookPath.path));
				books.add(initial_state.getGenre());
				dao.addBook(initial_state);
			}
			System.out.println( dao.getPageCount(100,20) == 5);
			assertEquals(dao.getPageCount(100,20), 5);
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        }
	}
}
