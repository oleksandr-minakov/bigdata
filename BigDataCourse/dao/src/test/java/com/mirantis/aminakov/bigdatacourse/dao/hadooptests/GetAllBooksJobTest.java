package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetAllBooksJob;

public class GetAllBooksJobTest {

	@Test
	public void getAllBooksJobTest() throws IOException, DaoException{
		

		BasicConfigurator.configure();
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "/bookshelf/books/");
		AddBookJob add = new AddBookJob(newOne);
		GetAllBooksJob get = new GetAllBooksJob(newOne);
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		
		for(int i=0; i<100; ++i){
			
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest"+i, "Test"+i, "Tester"+i, new FileInputStream("src/main/resources/testbook"));
			before.add(beggining_state);
			add.addBookJob(beggining_state);
		}
		after = get.getAllBooksJob(1, 100);
		assertEquals(before.size(),after.size());
		
	}
}
