package com.mirantis.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class GetRangedSliceTest {

	@Test
	public void getRangedSlicesTest() throws DaoException{
		
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		List<Book> after1 = new ArrayList<Book>();
		
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		EasyMock.expect(dao.getAllBooks(1, 40)).andStubReturn(before);
		EasyMock.expect(dao.getAllBooks(2, 20)).andStubReturn(before);
		EasyMock.replay(dao);
		EasyMock.verify(dao);
		
		after = dao.getAllBooks(1, 40);
		after1 = dao.getAllBooks(2, 20);
		
		System.out.println(after != null);
		System.out.println(after1 != null);
		
		assertTrue(after != null);
		assertTrue(after1 != null);
	}
}
