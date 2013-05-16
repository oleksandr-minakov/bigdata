package com.mirantis.bigdatacourse.dao.cassandratests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
			
			Book initial_state = new Book();
			initial_state.newBook("CassandraTest", "Test" + i, "Tester" + i, new FileInputStream("src/main/resources/testbook"));
			dao.addBook(initial_state);
		}
		
		books = dao.getBooksByToken(1, 100, "titles", "CassandraTest");
        PaginationModel model = new PaginationModel();

		model = dao.getAllBooks(1, 100);
		int count = dao.getNumberOfRecordsBy("titles", "CassandraTest");
		Assert.assertTrue(books.size() == count );
		Assert.assertTrue(model.getBooks().size() == count );
		cts.getCurrentClstr().dropKeyspace("KS");
	}
}
