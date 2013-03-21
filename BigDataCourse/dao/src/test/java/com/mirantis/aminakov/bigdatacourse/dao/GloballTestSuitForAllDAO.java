package com.mirantis.aminakov.bigdatacourse.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mirantis.aminakov.bigdatacourse.dao.cassandratests.CassandraGloballTestSuit;
import com.mirantis.aminakov.bigdatacourse.dao.hadooptests.HadoodGlobalTestSuit;
import com.mirantis.aminakov.bigdatacourse.dao.mysql.DaoJdbcTest;

@RunWith(Suite.class)

@SuiteClasses({HadoodGlobalTestSuit.class, CassandraGloballTestSuit.class, DaoJdbcTest.class})
public class GloballTestSuitForAllDAO {

}
