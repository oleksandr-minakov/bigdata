package com.mirantis.aminakov.bigdatacourse.dao.cassandra;

import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
@SuppressWarnings("unused")

public class Constants {

	public String CLUSTER_NAME;//= "Test Cluster";
	public String KEYSPACE_NAME;//= "Bookshelf";
	public String CF_NAME;//= "Books";
	public String HOST_DEF;//= "localhost";

	private Cluster clstr;

	private Keyspace ksOper;
	private BasicColumnFamilyDefinition CfDef;
	private BasicKeyspaceDefinition KsDef;
	
	public int bookID;
	
	private BasicKeyspaceDefinition getNewKeyspaceDef(String name){
		
			KsDef = new BasicKeyspaceDefinition();
			KsDef.setName(KEYSPACE_NAME);
			KsDef.setDurableWrites(true);
			KsDef.setReplicationFactor(1);
			KsDef.setStrategyClass("org.apache.cassandra.locator.SimpleStrategy");
			
			return this.KsDef;
	}
	
	public Keyspace getKeyspace(){
		
			return HFactory.createKeyspace(KEYSPACE_NAME, clstr); 
	}
	
	public BasicColumnFamilyDefinition getNewCfDef(String name){

			this.CfDef = new BasicColumnFamilyDefinition();
			this.CfDef.setKeyspaceName(KEYSPACE_NAME);
			this.CfDef.setName(CF_NAME);
			
			return this.CfDef;
	}
	
	public Cluster getCurrentClstr(){
		
		if(this.clstr == null){
			
			this.clstr = HFactory.getOrCreateCluster(this.CLUSTER_NAME, this.HOST_DEF+":9160");
			this.clstr.addKeyspace(this.KsDef);
			this.clstr.addColumnFamily(CfDef);
			
			return this.clstr;
		}
		else
			return this.clstr;
	}	

	public Constants(String clName, String ksName, String cfName, String hostDef){
		
		HOST_DEF = hostDef;
		CLUSTER_NAME = clName;
		KEYSPACE_NAME = ksName;
		CF_NAME = cfName;
		
		this.KsDef  = this.getNewKeyspaceDef(this.KEYSPACE_NAME);
		this.CfDef = getNewCfDef(this.CF_NAME);
		this.clstr = getCurrentClstr();
		
		this.bookID = 0;
	}

}
