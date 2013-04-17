package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

public class FormNewIDTest {

	@Test
	public void testCase() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		
		int testCond = 100; // adding <--- that amount of data to Cassandra
		int last = dao.getMaxIndex();
		int rounds = 50;
		Book beggining_state = new Book();
		try {
			for(int j = 0; j < rounds; ++j){
				for(int i = last; i< testCond + last; ++i){
			
					beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), "Test" + String.valueOf(i), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
					dao.addBook(beggining_state);
				}
				
			}
			
			System.out.println(dao.getMaxIndex() == testCond*rounds);
			
			assertEquals(dao.getMaxIndex(), testCond*rounds);
			
		}catch (Exception e) {throw new DaoException(e);}
		cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
	}
}
