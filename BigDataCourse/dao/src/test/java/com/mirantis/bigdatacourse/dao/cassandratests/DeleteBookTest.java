package com.mirantis.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


import org.easymock.EasyMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class DeleteBookTest {

	@Test
	public void deleteBookTest() throws DaoException {
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
//		Constants cts/* = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);cts */= EasyMock.createMock(Constants.class);
		
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		replay();
		Book beggining_state = new Book();
		
		try {
			
			beggining_state.setId(100);
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			
			expect(dao.addBook(beggining_state)).andReturn(100).times(1);
			expect(dao.delBook(100)).andReturn(100).times(2);
			dao.delBook(beggining_state.getId());
			verify();
			
			assertTrue(beggining_state.getId() != 0);
		} catch (Exception e) {throw new DaoException(e);}
	}
	
}
