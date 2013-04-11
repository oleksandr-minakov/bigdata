package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.service.CassandraHost;

import org.junit.Assert;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

public class ConnectionTest {
	
	
	@Test
	public void testConnection() throws DaoException{
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		
		cts.getCurrentClstr().addHost(new CassandraHost("0.0.0.0"), false);
		cts.getCurrentClstr().addHost(new CassandraHost("0.0.0.0"), false);
		
		DaoCassandra dao = new DaoCassandra(cts);
		
		Assert.assertNotEquals(dao, null);
	}

}
