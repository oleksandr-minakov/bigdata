package com.mirantis.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.DeleteBookJob;

public class DeleteBookJobTest {
	@SuppressWarnings("unused")
	@Test
	public void deleteBookJobTest()throws DaoException, IOException {
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");	
		newOne.bookID = 100;
		
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("testbook"));
		
		int resAdd = new AddBookJob(newOne).addBookJob(beggining_state);	
		int resDel = new DeleteBookJob(newOne).deleteBookJob(100);
		
		System.out.println("DeleteBookJobTest " + ( resDel == 0));
		assertEquals(resDel, 0);
	}

}
