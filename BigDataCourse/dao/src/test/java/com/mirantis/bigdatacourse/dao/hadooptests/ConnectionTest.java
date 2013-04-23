package com.mirantis.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class ConnectionTest {
	@Test
	public void getConnetionTest() throws DaoException, IOException{
		
		BasicConfigurator.configure();
		
		HadoopConnector newOne = new HadoopConnector( new HdfsIP().HadoopIP,"9000", new HdfsIP().HadoopUser, "/bookshelf/books/");		
		FileSystem fs = newOne.getFS();
		
		System.out.println("ConnectionTest " + ( fs != null ));
		
		assertNotNull(fs);
	}
}
