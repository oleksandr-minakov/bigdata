package com.mirantis.bigdatacourse.dao.newcassandratests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.NewConstants;
import com.mirantis.bigdatacourse.dao.cassandra.NewDaoCassandra;
import com.mirantis.bigdatacourse.dao.cassandratests.CassandraIP;

public class AddBookTest {
	
	@Test
	public void addBookTest() throws IOException, DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		NewConstants cts = new NewConstants("Cassandra Cluster", "KS", "Test", hosts);
		
		NewDaoCassandra dao = new NewDaoCassandra(cts);
		for(int i = 0; i < 100; ++i) {
			
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest", "Test"+i, "Tester"+i, new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
		}

		Assert.assertNotNull(cts);
		Assert.assertNotNull(dao);
		cts.getCurrentClstr().dropKeyspace("KS");
	}

}
