package com.mirantis.bigdatacourse.dao.cassandratests;

import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class GetAllRowKeysTest {
	
	@Test
	public void getIterList() throws DaoException{
		
		DaoCassandra dao = /*new DaoCassandra(cts);*/EasyMock.createMock(DaoCassandra.class);
		replay();
		EasyMock.expect(dao.getAllRowKeys()).andReturn(new ArrayList<String>()).times(1);
		List<String> keys = dao.getAllRowKeys();
		assertTrue(keys.size() != 0);
		EasyMock.verify(dao);
	}
}
