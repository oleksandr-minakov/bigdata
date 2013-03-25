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
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.DeleteBookJob;

public class DeleteBookJobTest {
	@SuppressWarnings("unused")
	@Test
	public void deleteBookJobTest()throws DaoException, IOException{
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP,"54310", new HdfsIP().HadoopUser, "/bookshelf/books/");	
		newOne.bookID = 100;
		
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
		
		int resAdd = new AddBookJob(newOne).addBookJob(beggining_state);	
		int resDel = new DeleteBookJob(newOne).deleteBookJob(100);
		
		System.out.println("DeleteBookJobTest " + ( resDel == 0));
		assertEquals(resDel, 0);
	}

}
