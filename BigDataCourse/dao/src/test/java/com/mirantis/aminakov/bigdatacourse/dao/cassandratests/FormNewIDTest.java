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

public class FormNewIDTest {

	@Test
	public void testCase() throws DaoException, InterruptedException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		
		int testCond = 10; // adding <--- that amount of data to Cassandra
		int last = dao.getMaxIndex();
		int rounds = 5;
		Book initial_state = new Book();
		try {
			for(int j = 0; j < rounds; ++j){
				for(int i = last; i< testCond + last; ++i){
					initial_state.newBook("CassandraTest" + String.valueOf(i),
                                            "Test" + String.valueOf(i),
                                            "Tester" + String.valueOf(i),
                                            new FileInputStream(BookPath.path));
					dao.addBook(initial_state);
				}
			}
			System.out.println(dao.getMaxIndex() == testCond*rounds);
			assertEquals(dao.getMaxIndex(), testCond * rounds);
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        }
	}
}
