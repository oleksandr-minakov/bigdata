package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GetAllRowKeysTest {

	@Test
	public void getIterList() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		Book initial_state = new Book();
		try {
			for(int i = 0; i < 100; ++i){
				initial_state.setId(i);
				initial_state.newBook("CassandraTest" + String.valueOf(i),
                                        "Test" + String.valueOf(i),
                                        "Tester" + String.valueOf(i),
                                        new FileInputStream("testbook"));
				dao.addBook(initial_state);
			}
            List<String> keys = dao.getAllRowKeys();
			assertTrue(keys.size() == 100);
		} catch (Exception e) {
            throw new DaoException(e);
        } finally {
		    cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        }
	}
}
