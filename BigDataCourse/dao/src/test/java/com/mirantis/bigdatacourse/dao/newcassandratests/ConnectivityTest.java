package com.mirantis.bigdatacourse.dao.newcassandratests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class ConnectivityTest {

	@Test
	public void getConnectionAndConfiguration(){
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "KS", "Test", hosts);
		
		DaoCassandra dao = new DaoCassandra(cts);
		
		cts.getCurrentClstr().dropKeyspace("KS");
		Assert.assertNotNull(cts);
		Assert.assertNotNull(dao);
	}
}
