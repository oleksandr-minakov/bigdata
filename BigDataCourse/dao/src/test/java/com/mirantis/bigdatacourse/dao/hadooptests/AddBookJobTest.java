package com.mirantis.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;

public class AddBookJobTest {
	@Test
	public void addBookTest()throws DaoException, IOException {
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP,"9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		
		newOne.bookID = 100;
		
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("testbook"));
		
		int res = new AddBookJob(newOne).addBookJob(beggining_state);
		res= new AddBookJob(newOne).addBookJob(beggining_state);
		
		System.out.println("AddBookJobTest " + (res == beggining_state.getId()) );
		
		assertEquals(res, beggining_state.getId());
		newOne.getFS().delete(new Path("/bookshelf/books/"+100+"/"), true);
		newOne.getFS().delete(new Path("/bookshelf/books/"+101+"/"), true);
	}
}
