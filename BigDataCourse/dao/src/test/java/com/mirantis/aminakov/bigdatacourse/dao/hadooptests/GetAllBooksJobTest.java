package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;

public class GetAllBooksJobTest {

	@Test
	public void getAllBooksJobTest() throws IOException, DaoException{
		

		BasicConfigurator.configure();
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "/bookshelf/books/");
		AddBookJob add = new AddBookJob(newOne);
		
		
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
		
		add.addBookJob(beggining_state);
	}
}
