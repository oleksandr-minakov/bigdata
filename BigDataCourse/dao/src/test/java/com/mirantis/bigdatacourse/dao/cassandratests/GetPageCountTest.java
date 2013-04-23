package com.mirantis.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;


import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class GetPageCountTest {

    @Test
	public void getPagesCountTest() throws DaoException {
		
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		EasyMock.expect(dao.getPageCount(0, 0)).andReturn(0).times(2);
		assertEquals(dao.getPageCount(100,20), 0);
		
	}
    
}
