package com.mirantis.bigdatacourse.dao.cassandratests;

import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Assert;
import org.junit.Test;

public class ConnectivityTest {

	@Test
	public void getConnectionAndConfiguration(){
		
		Constants cts = new Constants("Cassandra Cluster", "KS", "Test", CassandraIP.IP1);
		DaoCassandra dao = new DaoCassandra(cts);
		cts.getCurrentClstr().dropKeyspace("KS");
		Assert.assertNotNull(cts);
		Assert.assertNotNull(dao);
	}
}
