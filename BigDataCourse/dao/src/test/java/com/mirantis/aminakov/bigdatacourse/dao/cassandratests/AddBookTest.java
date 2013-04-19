package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.io.FileInputStream;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;

public class AddBookTest {

	@Test
	public void addBookTest() throws DaoException{
			
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		replay();
		
		Book beggining_state = new Book();
		try {
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			
			expect(dao.addBook(beggining_state)).andReturn(100).times(1);
			
			assertTrue(dao.addBook(beggining_state) == 0);
			
		} catch (Exception e) {throw new DaoException(e);}
		
	}
}
