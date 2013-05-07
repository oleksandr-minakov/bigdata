package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AddBookTest.class, BookConverterTest.class, DeleteBookTest.class,
	GetAllRowKeysTest.class, GetBookByTests.class, GetPageCountTest.class, 
	GetPageTest.class, GetRangedSliceTest.class, PaginateByTokenTest.class, 
	PaginationAndRemoving.class, FormNewIDTest.class})
public class CassandraGloballTestSuit {
	
	//Global test suit of cassandra misc
}
