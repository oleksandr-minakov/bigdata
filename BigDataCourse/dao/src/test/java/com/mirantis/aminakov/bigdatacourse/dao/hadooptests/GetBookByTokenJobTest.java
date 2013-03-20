package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByTokenJob;

public class GetBookByTokenJobTest {

	@Test
	public void test() throws DaoException, IOException {
		
		BasicConfigurator.configure();
		int testCase = 10;
		int pageSize = 5;
		int pageNum = 2;
		HadoopConnector newOne = new HadoopConnector("172.18.196.59","54310", "dmakogon", "/bookshelf/books/");
		
		AddBookJob add = new AddBookJob(newOne);
		GetBookByTokenJob get = new GetBookByTokenJob(newOne);
		
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		
		for(int i=0; i< testCase; ++i){
			 
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest"+i, "Test4", "Tester"+i, new FileInputStream("src/main/resources/testbook"));
			before.add(beggining_state);
			add.addBookJob(beggining_state);
		}
		
		 after = get.getBookByToken(pageNum, pageSize, "author","Test4");
		 if(after.size() == 0)
			 assertNotEquals(after.size(), before.size());
		 else
			 assertEquals(pageSize, after.size());
		
	}

}
