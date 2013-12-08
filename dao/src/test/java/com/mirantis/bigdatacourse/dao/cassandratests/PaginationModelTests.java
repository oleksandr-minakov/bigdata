package com.mirantis.bigdatacourse.dao.cassandratests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PaginationModelTests {

	/**
	 * 
	 * @throws DaoException 
	 * @throws FileNotFoundException 
	 */
	
	@Test
	public void testcase() throws DaoException, FileNotFoundException {

		List<String> hosts = new ArrayList<>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "KS", "Test", hosts.get(0));
		DaoCassandra dao = new DaoCassandra(cts);
		
		
		try{
			
			for(int i = 0; i < 100; ++i) {
			
				Book book = new Book();
				book.newBook("CassandraTest", "Test" + i, "Tester" + i, new FileInputStream("testbook"));
				dao.addBook(book);
			
			}
			
			PaginationModel books = dao.getAllBooks(1, dao.getAllRowKeys().size());
			int a = books.getNumberOfRecords();
			int b = dao.getAllRowKeys().size();
			Assert.assertEquals(a, b);
		
		} finally {
			
            cts.getCurrentClstr().dropKeyspace("KS");
        }
	}

}
