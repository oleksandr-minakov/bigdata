package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class ConnectionTest {
	
	@Test
	public void getConnetionTest() throws DaoException, IOException{
		
		BasicConfigurator.configure();
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "dmakogon", "/bookshelf/books/");		
		FileSystem fs = newOne.getFS();
		assertNotNull(fs);
		fs.close();
	}

}
