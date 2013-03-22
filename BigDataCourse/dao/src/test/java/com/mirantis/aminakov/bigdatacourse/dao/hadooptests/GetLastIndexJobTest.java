package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.DeleteBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetLastIndexJob;

public class GetLastIndexJobTest {

	@Test
	public void testCase() throws DaoException, IOException{
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "dmakogon", "/bookshelf/books/");
		newOne.bookID =1;
		AddBookJob add = new AddBookJob(newOne);
		GetLastIndexJob get = new GetLastIndexJob(newOne);
		
		int testCond = 1000; 
		
		for(int i= 0; i< testCond; ++i){
			
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest" + i, "Test", "Tester"+i, new FileInputStream("src/main/resources/testbook"));
			add.addBookJob(beggining_state);
		}
		
		for(int i = 999; i > 900; i--)
			new DeleteBookJob(newOne).deleteBookJob(i);
		
		Book book = new Book();
		book.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
		add.addBookJob(book);

		int res = get.getLastIndex();
		System.out.println("GetLastIndexJobTest" + (newOne.bookID - 1 == res));
		assertTrue(get.getIncrementedNewID() == newOne.bookID);
		
		for(int i = 1; i< 1002; ++i)
			newOne.getFS().delete(new Path("/bookshelf/books/"+i+"/"), true);
	}
}
