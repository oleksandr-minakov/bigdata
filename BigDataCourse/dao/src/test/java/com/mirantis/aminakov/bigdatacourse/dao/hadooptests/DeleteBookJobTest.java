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
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.DeleteBookJob;

public class DeleteBookJobTest {
	
	@Test
	public void deleteBookJobTest()throws DaoException, IOException{
		
		BasicConfigurator.configure();
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "/bookshelf/books/");		
		
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
		beggining_state.setId(100);
		
		int resAdd = new AddBookJob(newOne).addBookJob(beggining_state);	
		int resDel = new DeleteBookJob(newOne).deleteBookJob(100);
		newOne.closeConnection();
		
		assertEquals(resDel, resAdd);
	}

}
