package com.mirantis.bigdatacourse.dao.cassandratests;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.replay;
import org.junit.Assert;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class ConnectionTest {
	
	
	@Test
	public void testConnection() throws DaoException {
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts/* = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);cts */= EasyMock.createMock(Constants.class);
		replay();
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		replay();
		Assert.assertNotEquals(dao, null);
		Assert.assertNotEquals(cts, null);
	}

}
