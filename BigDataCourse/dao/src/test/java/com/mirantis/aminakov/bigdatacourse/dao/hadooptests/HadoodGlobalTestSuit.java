package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@Ignore
@RunWith(Suite.class)
@SuiteClasses({ AddBookJobTest.class, ConnectionTest.class,
		DeleteBookJobTest.class, GetAllBooksJobTest.class,
		GetBookByTokenJobTest.class, GetBookCountJobTest.class })
public class HadoodGlobalTestSuit {

}
