package com.mirantis.bigdatacourse.dao.cassandratests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeletionTest {
	
	@SuppressWarnings("unused")
    @Ignore
	@Test
	public void detetionTest() throws IOException, DaoException {
		
		List<String> hosts = new ArrayList<String>();
		List<Book> books = new ArrayList<Book>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "KS", "Test", CassandraIP.IP1);
		
		DaoCassandra dao = new DaoCassandra(cts);
		
		for(int i = 0; i < 100; ++i) {
			
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest"+i, "Test"+i, "Tester"+i, new FileInputStream("testbook"));
			dao.addBook(beggining_state);
			dao.delBook(beggining_state.getId());
		}

		cts.getCurrentClstr().dropKeyspace("KS");
	}

}
