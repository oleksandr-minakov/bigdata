package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

public class PaginationAndRemoving {
	
	@SuppressWarnings("unused")
	@Test
	public void testCase() throws DaoException{
		
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		List<Book> after1 = new ArrayList<Book>();
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", CassandraIP.IP);
		DaoCassandra dao = new DaoCassandra(cts);
		
		Book beggining_state = new Book();
		try {
			
			for(int i = 0; i< 10000; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				dao.addBook(beggining_state);
			}
		
			dao.delBook(5);
			dao.delBook(1);
			dao.delBook(7);
			after = dao.getAllBooks(1, 10000);
			System.out.print(dao.getNumberOfRecords());
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		}catch (Exception e) {throw new DaoException(e);}
	}
}
