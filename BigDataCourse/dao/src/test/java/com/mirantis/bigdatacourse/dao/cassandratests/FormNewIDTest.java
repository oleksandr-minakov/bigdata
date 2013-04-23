package com.mirantis.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class FormNewIDTest {

	@Test
	public void testCase() throws DaoException {
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
//		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = EasyMock.createMock(DaoCassandra.class);
		
		int testCond = 100; // adding <--- that amount of data to Cassandra
		int rounds = 50;

		try {
			
			EasyMock.expect(dao.getMaxIndex()).andReturn(testCond*rounds).times(2);
			EasyMock.replay(dao); 
			int last = dao.getMaxIndex();	

			System.out.println(dao.getMaxIndex() == testCond*rounds);
			assertEquals(last, testCond*rounds);
			EasyMock.verify(dao);
		}catch (Exception e) {throw new DaoException(e);}
	}
}
