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
		
		List<String> hosts = new ArrayList<>();
		List<Book> books;
		List<Book> books1 = new ArrayList<Book>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		Constants cts = new Constants("Cassandra Cluster", "KS", "Test", hosts.get(0));

		try{

			DaoCassandra dao = new DaoCassandra(cts);
			
			for(int i = 0; i < 100; ++i) {
				Book book = new Book();
				book.newBook("CassandraTest", "Test" + i, "Tester" + i, new FileInputStream("testbook"));
				books1.add(book);
				dao.addBook(book);
			}
			
			books = dao.getBooksByToken(1, 100, "titles", "CassandraTest");
	        PaginationModel model;
	        
	        dao.delBook(books1.get(0).getId());
	        
			model = dao.getAllBooks(1, 100);
			int count = dao.getNumberOfRecordsBy("titles", "CassandraTest");
			Assert.assertTrue(books.size() == count +1);
			Assert.assertTrue(model.getBooks().size() == count);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			cts.getCurrentClstr().dropKeyspace("KS");
		}
		
	}
}
