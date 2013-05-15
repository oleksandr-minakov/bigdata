package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AddBookJobTest {
	@Test
	public void addBookTest()throws DaoException, IOException {
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.bookID = 100;
		Book initial_state = new Book();
		initial_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("testbook"));
		int res = new AddBookJob(newOne).addBookJob(initial_state);
		System.out.println("AddBookJobTest " + (initial_state.getId().length() != 0));
		
		assertEquals(String.valueOf(res), initial_state.getId());
		newOne.getFS().delete(new Path("/bookshelf/books/" + 100 + "/"), true);
		newOne.getFS().delete(new Path("/bookshelf/books/" + 101 + "/"), true);
	}
}
