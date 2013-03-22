package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetAllBooksJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetLastIndexJob;

public class GetAllBooksJobTest {
	@Test
	public void getAllBooksJobTest() throws IOException, DaoException{
		

		BasicConfigurator.configure();
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "dmakogon", "/bookshelf/books/");
		GetLastIndexJob id = new GetLastIndexJob(newOne);
		newOne.bookID = id.getIncrementedNewID();
		
		AddBookJob add = new AddBookJob(newOne);
		GetAllBooksJob get = new GetAllBooksJob(newOne);
		
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		
		
		for(int i=0; i<10; ++i){
			
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest"+i, "Test"+i, "Tester"+i, new FileInputStream("src/main/resources/testbook"));
			before.add(beggining_state);
			add.addBookJob(beggining_state);
		}
		after = get.getAllBooksJob(1, 100);
		
		System.out.println("GetAllBooksJobTest " + ( before.size() == after.size() ));
		
		assertEquals(before.size(),after.size());
		
		for(int i = 1; i< 11; ++i)
			newOne.getFS().delete(new Path("/bookshelf/books/"+i+"/"), true);
	}
}
