package com.mirantis.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBooksCountJob;

public class GetBookCountJobTest {

	@Test
	public void testCase() throws DaoException, IOException {
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.bookID = 1;
		GetBooksCountJob count = new GetBooksCountJob(newOne);
		AddBookJob add = new AddBookJob(newOne);
		
		for(int i=0; i< 100; ++i){
			 
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest" + i%100, "Test", "Tester"+i%100, new FileInputStream("src/main/resource/testbook"));
			add.addBookJob(beggining_state);
		}
		
		System.out.println("GetBookCountJobTest " + (count.getBooksCount() == Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory))).size()));
		assertEquals(count.getBooksCount(), Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory))).size());
		
		for(int i = 1; i< 101; ++i)
			newOne.getFS().delete(new Path("/bookshelf/books/"+i+"/"), true);
	}
}
