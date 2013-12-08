package com.mirantis.bigdatacourse.dao.cassandratests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeletionTest {
	
	@SuppressWarnings("unused")
	@Test
	public void deletionTest() throws IOException, DaoException {
		
		List<String> hosts = new ArrayList<>();
		int[] results = new int[0];
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "KS", "Test", hosts.get(0));
		DaoCassandra dao = new DaoCassandra(cts);
		try {
            Book book = new Book();
            book.newBook("CassandraTest", "Test", "Tester", new FileInputStream("testbook"));
            dao.addBook(book);
            assertEquals(0, dao.delBook(book.getId()));
        } finally {
            cts.getCurrentClstr().dropKeyspace("KS");
        }
	}
}
