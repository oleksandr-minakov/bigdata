package com.mirantis.bigdatacourse.dao.cassandra;

import java.util.List;

import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
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
			this.CfDef.setName(name);
			return this.CfDef;
	}
	
	public Cluster getCurrentClstr() {
		
		boolean kspace_flg = false;
		boolean cfbooks_flg = false;
		boolean cftitles_flg = false;
		boolean cfauthors_flg = false;
		boolean cfgenres_flg = false;
		boolean cftexts_flg = false;
		
		if(this.clstr == null) {
			
			this.KsDef  = this.getNewKeyspaceDef();
			
			CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator();
			cassandraHostConfigurator.setAutoDiscoverHosts(true);
			cassandraHostConfigurator.setHosts(this.HOST_DEFS.get(0));
			cassandraHostConfigurator.setPort(9160);
			cassandraHostConfigurator.setMaxActive(1);
			
			this.clstr = HFactory.getOrCreateCluster(this.CLUSTER_NAME, cassandraHostConfigurator);
			

			
			for(KeyspaceDefinition def: clstr.describeKeyspaces()) {
				if(def.getName().equals(KEYSPACE_NAME)) {
					kspace_flg = true;
					for(ColumnFamilyDefinition cf:def.getCfDefs()) {
						
						if(cf.getName().equals(CF_NAME)) 
							cfbooks_flg = true;
					
						if(cf.getName().equals("titles")) 
							cftitles_flg = true;
						
						if(cf.getName().equals("authors")) 
							cfauthors_flg = true;
						
						if(cf.getName().equals("genres")) 
							cfgenres_flg = true;
						
						if(cf.getName().equals("texts")) 
							cftexts_flg = true;
						
					}
				}
			}
			
			if(kspace_flg == false) {
				this.clstr.addKeyspace(KsDef);
			}
			
			for(KeyspaceDefinition def: clstr.describeKeyspaces()) {
				
				if(def.getName().equals(KEYSPACE_NAME)) {
					
					if(cfbooks_flg == false) {
						this.CfDef = getNewCfDef(this.CF_NAME);
						this.clstr.addColumnFamily(CfDef);
					}
					
					if(cftitles_flg == false) {
						BasicColumnFamilyDefinition newCF = getNewCfDef("titles");
						this.clstr.addColumnFamily(newCF);
					}
					
					if(cfauthors_flg == false) {
						BasicColumnFamilyDefinition newCF = getNewCfDef("authors");
						this.clstr.addColumnFamily(newCF);
					}
					
					if(cfgenres_flg == false) {
						BasicColumnFamilyDefinition newCF = getNewCfDef("genres");
						this.clstr.addColumnFamily(newCF);
					}
					
					if(cftexts_flg == false) {
						BasicColumnFamilyDefinition newCF = getNewCfDef("texts");
						this.clstr.addColumnFamily(newCF);
					}
				}
			}
			return this.clstr;
		}
		else
			return this.clstr;
	}	

	public Constants(String clName, String ksName, String cfName, List<String> hostDef) {
		
		HOST_DEFS = hostDef;
		CLUSTER_NAME = clName;
		KEYSPACE_NAME = ksName;
		CF_NAME = cfName;
		
		this.clstr = getCurrentClstr();
		
		for(String host: HOST_DEFS)	
			this.clstr.addHost(new CassandraHost(host), false);
		
	}

}
