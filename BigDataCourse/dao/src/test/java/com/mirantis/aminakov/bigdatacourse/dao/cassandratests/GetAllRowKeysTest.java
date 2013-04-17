package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class GetAllRowKeysTest {
	
	@Test
	public void getIterList() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		
		DaoCassandra dao = new DaoCassandra(cts);
		
/*		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 100; ++i){
				
				beggining_state.setId(i);
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				dao.addBook(beggining_state);
			}

			
			System.out.println( keys.size() == 100);
			
			assertTrue(keys.size() == 100);
			
		} catch (Exception e) {throw new DaoException(e);}
		cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
	}*/
		List<String> keys = dao.getAllRowKeys();
		assertTrue(keys.size() != 0);
	}
}
