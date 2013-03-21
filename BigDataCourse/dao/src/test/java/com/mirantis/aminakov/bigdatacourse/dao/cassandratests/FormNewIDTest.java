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
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", CassandraIP.IP);
		DaoCassandra dao = new DaoCassandra(cts);
		
		int testCond = 100; // adding <--- that amount of data to Cassandra
		int last = dao.getMaxIndex();
		int rounds = 5;
		Book beggining_state = new Book();
		try {
			for(int j = 0; j < rounds; ++j){
				if(last == 0){
					for(int i = last; i< testCond + last; ++i){
				
						beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), "Test" + String.valueOf(i), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
						dao.addBook(beggining_state);
					}
				}
				else
					for(int i = last; i< testCond + last+j; ++i){
					
						beggining_state.newBook(new String("CassandraTest" + String.valueOf(j)), "Test" + String.valueOf(j), new String("Tester" + String.valueOf(j)), new FileInputStream("src/main/resources/testbook"));
						dao.addBook(beggining_state);
					}
			}
			
			System.out.println(dao.getMaxIndex() == testCond*rounds);
			
			assertEquals(dao.getMaxIndex(), testCond*rounds);
			
		}catch (Exception e) {throw new DaoException(e);}
		cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
	}
}
