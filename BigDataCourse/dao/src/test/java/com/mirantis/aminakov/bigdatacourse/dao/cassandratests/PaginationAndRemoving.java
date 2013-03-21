package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

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
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", CassandraIP.IP);
		DaoCassandra dao = new DaoCassandra(cts);
		
		int testCond = 100; // adding that amount of data to Cassandra
		int testIndexBlanker = 10; // cutting indexes of rows
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		List<Book> after1 = new ArrayList<Book>();
		
		Book beggining_state = new Book();
		try {
			
			for(int i = 0; i< testCond; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), "Test" + String.valueOf(i%testIndexBlanker), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				dao.addBook(beggining_state);
			}
		
			dao.delBook(6);
			dao.delBook(1);
			dao.delBook(7);
			
			after1 = dao.getBookByAuthor(1, 1000, "Test4");
			if(testCond%testIndexBlanker == 0){
				
				System.out.println(dao.getNumberOfRecords() == testCond/testIndexBlanker);
				assertEquals(dao.getNumberOfRecords(), testCond/testIndexBlanker);
			}
			else{
				
				System.out.println(dao.getNumberOfRecords() == testCond/testIndexBlanker + 1);
				assertEquals(dao.getNumberOfRecords(), testCond/testIndexBlanker +1);
			}
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		}catch (Exception e) {throw new DaoException(e);}
	}
}
