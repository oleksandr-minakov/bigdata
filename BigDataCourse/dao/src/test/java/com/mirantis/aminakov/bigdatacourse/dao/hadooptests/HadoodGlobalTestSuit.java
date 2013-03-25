package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Suite.class)
@SuiteClasses({ ConnectionTest.class, AddBookJobTest.class,
		DeleteBookJobTest.class, GetAllBooksJobTest.class,
		GetBookByTokenJobTest.class, GetBookCountJobTest.class, GetLastIndexJobTest.class})
public class HadoodGlobalTestSuit {
}
