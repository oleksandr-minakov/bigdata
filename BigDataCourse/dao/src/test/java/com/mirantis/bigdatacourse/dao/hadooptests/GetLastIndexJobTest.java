package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.DeleteBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetLastIndexJob;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GetLastIndexJobTest {

	@Test
	public void testCase() throws DaoException, IOException {
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.bookID = 1;
		AddBookJob add = new AddBookJob(newOne);
		GetLastIndexJob get = new GetLastIndexJob(newOne);
		int testCond = 100;
		
		for(int i = 0; i < testCond; ++i){
			Book initial_state = new Book();
			initial_state.newBook("CassandraTest" + i, "Test", "Tester" + i, new FileInputStream("testbook"));
			add.addBookJob(initial_state);
		}

		for(int i = 99; i > 90; i--)
			new DeleteBookJob(newOne).deleteBookJob(String.valueOf(i));
		
		Book book = new Book();
		book.newBook("CassandraTest", "Test", "Tester", new FileInputStream("testbook"));
		add.addBookJob(book);
		int res = get.getLastIndex();
		System.out.println("GetLastIndexJobTest" + (newOne.bookID - 1 == res));
		assertTrue(get.getIncrementedNewID() == newOne.bookID);
		
		for(int i = 1; i < 102; ++i)
			newOne.getFS().delete(new Path("/bookshelf/books/" + i + "/"), true);
	}
}
