package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;

public class GetPageTest {

	@Test
	public void getpagesTest() throws DaoException {

		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		EasyMock.expect(dao.getAllBooks(1, 100)).andReturn(new ArrayList<Book>()).times(1);
		assertEquals(dao.getAllBooks(1, 100), null);
		
	}
		
}

