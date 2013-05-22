package com.mirantis.bigdatacourse.dao.cassandra;

import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import org.springframework.beans.factory.annotation.Value;

public class Constants {
	
	@Value("#{properties.cluster}")
	public String CLUSTER_NAME;
	
	@Value("#{properties.keyspace}")
	public String KEYSPACE_NAME;
	
	@Value("#{properties.cfamily}")
	public String CF_NAME;
	
	@Value("#{properties.node1}")
	public String HOST;
	
	private Cluster cluster;
	private BasicColumnFamilyDefinition columnFamilyDefinition;
	private BasicKeyspaceDefinition keyspaceDefinition;

	private BasicKeyspaceDefinition getNewKeyspaceDef() {
			keyspaceDefinition = new BasicKeyspaceDefinition();
			keyspaceDefinition.setName(KEYSPACE_NAME);
			keyspaceDefinition.setDurableWrites(true);
			keyspaceDefinition.setReplicationFactor(1);
			keyspaceDefinition.setStrategyClass("org.apache.cassandra.locator.SimpleStrategy");
			return this.keyspaceDefinition;
	}
	
	public Keyspace getKeyspace() {
			return HFactory.createKeyspace(KEYSPACE_NAME, cluster);
	}
	
	public BasicColumnFamilyDefinition getNewCfDef(String name) {
			this.columnFamilyDefinition = new BasicColumnFamilyDefinition();
			this.columnFamilyDefinition.setKeyspaceName(KEYSPACE_NAME);
			this.columnFamilyDefinition.setName(name);
			return this.columnFamilyDefinition;
	}
	
	public Cluster getCurrentClstr() {
		
		boolean keyspaceFlag = false;
		boolean columnFamilyBooksFlag = false;
		boolean columnFamilyTitlesFlag = false;
		boolean columnFamilyAuthorsFlag = false;
		boolean columnFamilyGenresFlag = false;
		boolean columnFamilyTextsFlag = false;

        if (this.cluster == null) {
            this.keyspaceDefinition = this.getNewKeyspaceDef();

            CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator();
            cassandraHostConfigurator.setHosts(HOST);
            cassandraHostConfigurator.setAutoDiscoverHosts(true);
            cassandraHostConfigurator.setPort(9160);
            cassandraHostConfigurator.setMaxActive(1);
            cassandraHostConfigurator.setCassandraThriftSocketTimeout(60 * 100);

            this.cluster = HFactory.getOrCreateCluster(this.CLUSTER_NAME, cassandraHostConfigurator);

            for (KeyspaceDefinition def : cluster.describeKeyspaces()) {
                if (def.getName().equals(KEYSPACE_NAME)) {
                    keyspaceFlag = true;
                    for (ColumnFamilyDefinition cf : def.getCfDefs()) {

                        if (cf.getName().equals(CF_NAME))
                            columnFamilyBooksFlag = true;

                        if (cf.getName().equals("titles"))
                            columnFamilyTitlesFlag = true;

                        if (cf.getName().equals("authors"))
                            columnFamilyAuthorsFlag = true;

                        if (cf.getName().equals("genres"))
                            columnFamilyGenresFlag = true;

                        if (cf.getName().equals("texts"))
                            columnFamilyTextsFlag = true;
                    }
                }
            }

            if (!keyspaceFlag) {
                this.cluster.addKeyspace(keyspaceDefinition);
            }

            for (KeyspaceDefinition def : cluster.describeKeyspaces()) {
                if (def.getName().equals(KEYSPACE_NAME)) {
                    if (!columnFamilyBooksFlag) {
                        this.columnFamilyDefinition = getNewCfDef(this.CF_NAME);
                        this.cluster.addColumnFamily(columnFamilyDefinition);
                    }
                    if (!columnFamilyTitlesFlag) {
                        BasicColumnFamilyDefinition newCF = getNewCfDef("titles");
                        this.cluster.addColumnFamily(newCF);
                    }
                    if (!columnFamilyAuthorsFlag) {
                        BasicColumnFamilyDefinition newCF = getNewCfDef("authors");
                        this.cluster.addColumnFamily(newCF);
                    }
                    if (!columnFamilyGenresFlag) {
                        BasicColumnFamilyDefinition newCF = getNewCfDef("genres");
                        this.cluster.addColumnFamily(newCF);
                    }
                    if (!columnFamilyTextsFlag) {
                        BasicColumnFamilyDefinition newCF = getNewCfDef("texts");
                        this.cluster.addColumnFamily(newCF);
                    }
                }
            }
            return this.cluster;
        } else {
            return this.cluster;
        }
	}

	public Constants(String clName, String ksName, String cfName, String host) {
		HOST = host;
		CLUSTER_NAME = clName;
		KEYSPACE_NAME = ksName;
		CF_NAME = cfName;
		this.cluster = getCurrentClstr();
	}
}