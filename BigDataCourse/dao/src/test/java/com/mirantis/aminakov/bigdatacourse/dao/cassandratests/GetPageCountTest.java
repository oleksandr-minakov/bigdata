package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.*;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class GetPageCountTest {

	@Test
	public void getPagesCountTest() throws DaoException{
		
		BasicConfigurator.configure();
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "0.0.0.0");
		DaoCassandra dao = new DaoCassandra(cts);

		List<String> books = new ArrayList<String>();
		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 1000; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				books.add(beggining_state.getGenre());
				dao.addBook(beggining_state);
			}
			assertEquals(dao.getPageCount(cts.bookID,20), 50);
		} catch (Exception e) {throw new DaoException(e);}
	}
}
