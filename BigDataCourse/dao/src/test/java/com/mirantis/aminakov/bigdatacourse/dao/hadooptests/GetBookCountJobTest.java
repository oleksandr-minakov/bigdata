package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.junit.Ignore;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBooksCountJob;

public class GetBookCountJobTest {
	@Ignore
	@Test
	public void testCase() throws DaoException, IOException{
		
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "dmakogon", "/bookshelf/books/");
		GetBooksCountJob count = new GetBooksCountJob(newOne);
		AddBookJob add = new AddBookJob(newOne);
		
		for(int i=0; i< 100; ++i){
			 
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest" + i%100, "Test", "Tester"+i%100, new FileInputStream("src/main/resources/testbook"));
			add.addBookJob(beggining_state);
		}
		assertNotEquals(count.getBooksCount(), 0);
		
		for(FileStatus fs: Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory)))){
			newOne.getFS().delete(fs.getPath(), true);
		}
	}
}
