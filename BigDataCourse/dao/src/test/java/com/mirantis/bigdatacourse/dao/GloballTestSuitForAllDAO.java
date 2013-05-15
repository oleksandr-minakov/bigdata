package com.mirantis.bigdatacourse.dao;

import com.mirantis.bigdatacourse.dao.hadooptests.HadoopGlobalTestSuit;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mirantis.bigdatacourse.dao.mysql.DaoJdbcTest;

@RunWith(Suite.class)

@SuiteClasses({HadoopGlobalTestSuit.class, DaoJdbcTest.class})
public class GloballTestSuitForAllDAO {

}
