package com.mirantis.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class GetBookByTests {

	@Test
	public void getBookByTitleTest() throws DaoException {
		
		List<Book> after = new ArrayList<Book>();
		
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		EasyMock.replay();
		EasyMock.expect(dao.getBookByTitle(1, 10, new String("CassandraTest4"))).andReturn(new ArrayList<Book>()).times(1);
		after = dao.getBookByTitle(1, 10, new String("CassandraTest4"));
		assertTrue(after == null);
		System.out.println(after == null);
		EasyMock.verify();
			
	}
	
	@Test
	public void getBookByAuthorTest() throws DaoException {
		
		List<Book> after = new ArrayList<Book>();
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		EasyMock.replay();
		
		EasyMock.expect(dao.getBookByAuthor(1, 10, new String("CassandraTest4"))).andReturn(new ArrayList<Book>()).times(1);
		after = dao.getBookByAuthor(1, 10, new String("CassandraTest4"));
		assertTrue(after == null);
		System.out.println(after == null);
	}
	
	@Test
	public void getBookByGenreTest() throws DaoException {
		
		List<Book> after = new ArrayList<Book>();
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		EasyMock.replay();

		EasyMock.expect(dao.getBookByGenre(1, 10, new String("CassandraTest4"))).andReturn(new ArrayList<Book>()).times(1);
		after = dao.getBookByGenre(1, 10, new String("CassandraTest4"));
		assertTrue(after == null);
		System.out.println(after == null);
	}
	
	@Test
	public void getAuthorByGenreTest() throws DaoException {
		
		TreeSet<String> after = new TreeSet<String>();
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		EasyMock.replay();
		
		EasyMock.expect(dao.getAuthorByGenre(1, 10, new String("CassandraTest4"))).andReturn(new TreeSet<String>()).times(1);
		after = dao.getAuthorByGenre(1, 10, new String("CassandraTest4"));
		assertTrue(after == null);
		System.out.println(after == null);
		
	}
	
}
