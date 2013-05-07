package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PaginationAndRemoving {
	
	@Test
	public void testCase() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		int testCond = 100; // adding that amount of data to Cassandra
		int testIndexBlanker = 10; // cutting indexes of rows
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		List<Book> after1 = new ArrayList<Book>();
		Book initial_state = new Book();
		try {
			for(int i = 0; i < testCond; ++i){
				initial_state.newBook("CassandraTest" + String.valueOf(i),
                                        "Test" + String.valueOf(i % testIndexBlanker),
                                        "Tester" + String.valueOf(i),
                                        new FileInputStream("testbook"));
				dao.addBook(initial_state);
			}
			dao.delBook(6);
			dao.delBook(1);
			dao.delBook(7);
			after1 = dao.getBookByAuthor(1, 1000, "Test4");
			if(testCond % testIndexBlanker == 0) {
				System.out.println(dao.getNumberOfRecords() == testCond / testIndexBlanker);
				assertEquals(dao.getNumberOfRecords(), testCond / testIndexBlanker);
			} else {
				System.out.println(dao.getNumberOfRecords() == testCond / testIndexBlanker + 1);
				assertEquals(dao.getNumberOfRecords(), testCond / testIndexBlanker + 1);
			}
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        }
	}
}
