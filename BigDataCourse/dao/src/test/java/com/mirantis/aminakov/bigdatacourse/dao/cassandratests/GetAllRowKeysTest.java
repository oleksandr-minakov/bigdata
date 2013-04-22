package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

import org.easymock.EasyMock;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;

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
