package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetAllBooksJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetLastIndexJob;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetAllBooksJobTest {

	@Test
	public void getAllBooksJobTest() throws IOException, DaoException {

		BasicConfigurator.configure();

		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		GetLastIndexJob id = new GetLastIndexJob(newOne);
		newOne.bookID = id.getIncrementedNewID();
		
		AddBookJob add = new AddBookJob(newOne);
		GetAllBooksJob get = new GetAllBooksJob(newOne);
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		
		
		for(int i = 0; i < 10; ++i) {
			Book initial_state = new Book();
			initial_state.newBook("CassandraTest" + i, "Test" + i, "Tester" + i, new FileInputStream("testbook"));
			before.add(initial_state);
			add.addBookJob(initial_state);
		}
		after = get.getAllBooksJob(1, 10);
		System.out.println("GetAllBooksJobTest " + ( before.size() == after.size() ));
		assertEquals(before.size(), after.size());
		for(int i = 1; i < 11; ++i) {
            newOne.getFS().delete(new Path("/bookshelf/books/" + i + "/"), true);
        }
	}
}
