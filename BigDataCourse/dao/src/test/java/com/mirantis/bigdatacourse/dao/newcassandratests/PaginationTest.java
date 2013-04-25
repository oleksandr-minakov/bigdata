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

public class PaginationTest {
	
	@Test
	public void paginateIt() throws DaoException, IOException{
		
		List<String> hosts = new ArrayList<String>();
		List<Book> books = new ArrayList<Book>();
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
		
		books = dao.getBooksByToken(1, 20, "titles", "CassandraTest");
		Assert.assertTrue(books.size() == 1);
		cts.getCurrentClstr().dropKeyspace("KS");
	}

}
