package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;

public class PaginateByTokenTest {

	@Test
	public void paginationTest() throws DaoException, FileNotFoundException{
		
		List<Book> res = new ArrayList<Book>();
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);

		EasyMock.expect(dao.getBooksByToken("Test0","book author")).andStubReturn(res);
		EasyMock.expect(dao.getBooksByToken("Test1","book genre")).andStubReturn(res);
		EasyMock.expect(dao.getBooksByToken("CassandraTest0","book title")).andStubReturn(res);
		EasyMock.replay(dao);
		EasyMock.verify(dao);
		
		List<Book> lst1 = dao.getBooksByToken("Test0","book author");

		List<Book> lst2 = dao.getBooksByToken("Test1","book genre");
		
		List<Book> lst3 = dao.getBooksByToken("CassandraTest0","book title");
		
		assertTrue(lst2 != null);
		assertTrue(lst1 != null);
		assertTrue(lst3 != null);
		
	}

}
