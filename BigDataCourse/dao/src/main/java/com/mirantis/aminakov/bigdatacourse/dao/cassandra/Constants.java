package com.mirantis.aminakov.bigdatacourse.dao.cassandra;

import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import java.util.List;
@SuppressWarnings("unused")

public class Constants {

	public String CLUSTER_NAME;//= "Test Cluster";
	public String KEYSPACE_NAME;//= "Bookshelf";
	public String CF_NAME;//= "Books";
	public List<String> HOST_DEFS;//= "localhost";

	private Cluster clstr;

	private Keyspace ksOper;
	private BasicColumnFamilyDefinition CfDef;
	private BasicKeyspaceDefinition KsDef;
	
	public int bookID;
	
	private BasicKeyspaceDefinition getNewKeyspaceDef() {
        KsDef = new BasicKeyspaceDefinition();
        KsDef.setName(KEYSPACE_NAME);
        KsDef.setDurableWrites(true);
        KsDef.setReplicationFactor(1);
        KsDef.setStrategyClass("org.apache.cassandra.locator.SimpleStrategy");
        return this.KsDef;
	}
	
	public Keyspace getKeyspace() {
        return HFactory.createKeyspace(KEYSPACE_NAME, clstr);
	}
	
	public BasicColumnFamilyDefinition getNewCfDef(String name) {
        this.CfDef = new BasicColumnFamilyDefinition();
        this.CfDef.setKeyspaceName(KEYSPACE_NAME);
        this.CfDef.setName(CF_NAME);
        return this.CfDef;
	}
	
	public Cluster getCurrentClstr() {
		if(this.clstr == null) {
			this.KsDef  = this.getNewKeyspaceDef();
			this.CfDef = getNewCfDef(this.CF_NAME);
			CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator();
			cassandraHostConfigurator.setAutoDiscoverHosts(true);
			cassandraHostConfigurator.setHosts(this.HOST_DEFS.get(0));
			cassandraHostConfigurator.setPort(9160);
			cassandraHostConfigurator.setMaxActive(1);
			this.clstr = HFactory.getOrCreateCluster(this.CLUSTER_NAME, cassandraHostConfigurator);
//			this.clstr = HFactory.getOrCreateCluster(this.CLUSTER_NAME, this.HOST_DEFS.get(0)+":9160");
			boolean flg = false;
			boolean cf_flg = false;
			for(KeyspaceDefinition def: clstr.describeKeyspaces()) {
				if(def.getName().equals(KEYSPACE_NAME)){
					flg = true;
					for(ColumnFamilyDefinition cf:def.getCfDefs()) {
						if(cf.getName().equals(CF_NAME)){
							cf_flg = true;
						}
					}
				}
			}
			if(!flg) {
				this.clstr.addKeyspace(KsDef);
			}
			for(KeyspaceDefinition def: clstr.describeKeyspaces()) {
				if(def.getName().equals(KEYSPACE_NAME)){
					if(!cf_flg){
						this.CfDef = getNewCfDef(this.CF_NAME);
						this.clstr.addColumnFamily(CfDef);
					}
				}
			}
			return this.clstr;
		} else {
            return this.clstr;
        }
	}

	public Constants(String clName, String ksName, String cfName, List<String> hostDef){
		
		HOST_DEFS = hostDef;
		CLUSTER_NAME = clName;
		KEYSPACE_NAME = ksName;
		CF_NAME = cfName;
		this.clstr = getCurrentClstr();
		for(String host: HOST_DEFS)
			this.clstr.addHost(new CassandraHost(host), false);
		this.bookID = 0;
	}
}
