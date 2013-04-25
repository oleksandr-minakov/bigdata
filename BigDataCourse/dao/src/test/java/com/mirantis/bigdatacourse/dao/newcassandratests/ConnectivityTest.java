package com.mirantis.bigdatacourse.dao.newcassandratests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.cassandra.NewConstants;
import com.mirantis.bigdatacourse.dao.cassandra.NewDaoCassandra;
import com.mirantis.bigdatacourse.dao.cassandratests.CassandraIP;

public class ConnectivityTest {

	@Test
	public void getConnectionAndConfiguration(){
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		NewConstants cts = new NewConstants("Cassandra Cluster", "KS", "Test", hosts);
		
		NewDaoCassandra dao = new NewDaoCassandra(cts);
		
		cts.getCurrentClstr().dropKeyspace("KS");
		Assert.assertNotNull(cts);
		Assert.assertNotNull(dao);
	}
}
