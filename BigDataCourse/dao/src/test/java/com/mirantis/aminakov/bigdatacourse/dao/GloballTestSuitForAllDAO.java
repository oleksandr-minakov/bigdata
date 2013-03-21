package com.mirantis.aminakov.bigdatacourse.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mirantis.aminakov.bigdatacourse.dao.cassandratests.CassandraGloballTestSuit;
import com.mirantis.aminakov.bigdatacourse.dao.hadooptests.HadoodGlobalTestSuit;

@RunWith(Suite.class)

@SuiteClasses({HadoodGlobalTestSuit.class, CassandraGloballTestSuit.class})
public class GloballTestSuitForAllDAO {

}
