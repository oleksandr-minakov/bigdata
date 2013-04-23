package com.mirantis.bigdatacourse.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mirantis.bigdatacourse.dao.cassandratests.CassandraGloballTestSuit;
import com.mirantis.bigdatacourse.dao.hadooptests.HadoodGlobalTestSuit;
import com.mirantis.bigdatacourse.dao.mysql.DaoJdbcTest;

@RunWith(Suite.class)

@SuiteClasses({HadoodGlobalTestSuit.class, CassandraGloballTestSuit.class, DaoJdbcTest.class})
public class GloballTestSuitForAllDAO {

}
