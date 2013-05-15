package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.DeleteBookJob;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DeleteBookJobTest {
	@SuppressWarnings("unused")
	@Test
	public void deleteBookJobTest()throws DaoException, IOException {
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");	
		newOne.bookID = 100;
		
		Book initial_state = new Book();
		initial_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("testbook"));
		
		int resAdd = new AddBookJob(newOne).addBookJob(initial_state);
		int resDel = new DeleteBookJob(newOne).deleteBookJob(String.valueOf(100));
		
		System.out.println("DeleteBookJobTest " + ( resDel == 0));
		assertEquals(resDel, 0);
	}

}
