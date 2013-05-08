package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandratests.BookPath;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.DeleteBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetLastIndexJob;

public class GetLastIndexJobTest {

	@Test
	public void testCase() throws DaoException, IOException{
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.bookID =1;
		AddBookJob add = new AddBookJob(newOne);
		GetLastIndexJob get = new GetLastIndexJob(newOne);
		
		int testCond = 100;
		
		for(int i= 0; i< testCond; ++i){
			
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest" + i, "Test", "Tester"+i, new FileInputStream(BookPath.path));
			add.addBookJob(beggining_state);
		}
		
		for(int i = 99; i > 90; i--)
			new DeleteBookJob(newOne).deleteBookJob(i);
		
		Book book = new Book();
		book.newBook("CassandraTest", "Test", "Tester", new FileInputStream(BookPath.path));
		add.addBookJob(book);

		int res = get.getLastIndex();
		System.out.println("GetLastIndexJobTest " + (newOne.bookID != res));
		assertTrue(get.getIncrementedNewID() != newOne.bookID);
		
		for(int i = 1; i< 102; ++i)
			newOne.getFS().delete(new Path("/bookshelf/books/"+i+"/"), true);
	}
}
