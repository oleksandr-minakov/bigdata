package com.mirantis.bigdatacourse.dao.newcassandratests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class PaginationTest {
	
	@Test
	public void paginateIt() throws DaoException, IOException{
		
		List<String> hosts = new ArrayList<String>();
		List<Book> books = new ArrayList<Book>();
		List<Book> getAllBooks = new ArrayList<Book>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "KS", "Test", hosts);
		
		DaoCassandra dao = new DaoCassandra(cts);
		
		for(int i = 0; i < 100; ++i) {
			
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest", "Test"+i, "Tester"+i, new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
		}
		
		books = dao.getBooksByToken(1, 100, "titles", "CassandraTest");
		getAllBooks = dao.getAllBooks(1, 100);
		int count = dao.getNumberOfRecordsBy("titles", "CassandraTest");
		
		Assert.assertTrue(books.size() == count );
		Assert.assertTrue(getAllBooks.size() == count );
		cts.getCurrentClstr().dropKeyspace("KS");
	}

}
