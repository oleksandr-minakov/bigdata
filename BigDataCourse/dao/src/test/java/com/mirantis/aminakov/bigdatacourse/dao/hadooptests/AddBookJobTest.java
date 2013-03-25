package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
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
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP,"54310", "hduser", "/bookshelf/books/");
		
		newOne.bookID = 100;
		
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
		
		int res = new AddBookJob(newOne).addBookJob(beggining_state);
		res= new AddBookJob(newOne).addBookJob(beggining_state);
		
		System.out.println("AddBookJobTest " + (res == beggining_state.getId()) );
		
		assertEquals(res, beggining_state.getId());
		newOne.getFS().delete(new Path("/bookshelf/books/"+100+"/"), true);
		newOne.getFS().delete(new Path("/bookshelf/books/"+101+"/"), true);
	}
}
