package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ConnectionTest {
	@Test
	public void getConnetionTest() throws DaoException, IOException {
		
		BasicConfigurator.configure();
		HadoopConnector newOne = new HadoopConnector( new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books_dev/", 1);
		FileSystem fs = newOne.getFS();
		System.out.println("ConnectionTest " + ( fs != null ));
		assertNotNull(fs);
	}
}
