package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.*;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class GetPageCountTest {

    @Test
	public void getPagesCountTest() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);

		List<String> books = new ArrayList<String>();
		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 100; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				books.add(beggining_state.getGenre());
				dao.addBook(beggining_state);
			}
			
			System.out.println( dao.getPageCount(100,20) == 5);
			
			assertEquals(dao.getPageCount(100,20), 5);
			
		} catch (Exception e) {throw new DaoException(e);}
	}
}
