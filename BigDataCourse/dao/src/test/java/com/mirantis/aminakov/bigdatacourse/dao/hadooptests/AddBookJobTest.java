package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;

public class AddBookJobTest {
	
	@Test
	public void addBookTest()throws DaoException, IOException{
		
		BasicConfigurator.configure();
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "dmakogon", "/bookshelf/books/");
		
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
		
		int res = new AddBookJob(newOne).addBookJob(beggining_state);
		res= new AddBookJob(newOne).addBookJob(beggining_state);
		assertEquals(res, beggining_state.getId());
	}
}
