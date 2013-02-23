package com.mirantis.aminakov.bigdatacourse.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddBookTest.class, BookConvTest.class, DeleteBookTest.class,
	GetAllRowKeysTest.class, GetBookByTests.class, GetPageCountTest.class, GetPageTest.class, GetRangedSliceTest.class })
public class GloballTestCase {

}
