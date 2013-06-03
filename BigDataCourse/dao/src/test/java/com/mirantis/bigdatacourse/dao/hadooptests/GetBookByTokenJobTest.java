package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.*;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetBookByTokenJobTest {

	@SuppressWarnings("unused")
	@Test
	public void test() throws DaoException, IOException {
		
		int testCase = 10;
		int pageSize = 10000;
		int pageNum = 2;
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.bookID = 1;
		
		AddBookJob add = new AddBookJob(newOne);
		GetBookByTokenJob get = new GetBookByTokenJob(newOne);
		GetBookByTitleJob getTitle = new GetBookByTitleJob(newOne);
		GetBookByAuthorJob getAuthor = new GetBookByAuthorJob(newOne);
		GetBookByGenreJob getGenre = new GetBookByGenreJob(newOne);
		
		List<Book> before = new ArrayList<Book>();
		List<Book> afterGetTitle;
		List<Book> afterGetTitle1;
		List<Book> afterGetGenre;
		List<Book> afterGetGenre1;
		List<Book> afterGetAuthor;
		List<Book> afterGetAuthor1;
		
		for(int i = 0; i < 100; ++i) {
			
			Book book = new Book();
			book.newBook("Test", "author", "genre", new FileInputStream("testbook"));
			add.addBookJob(book);
			
		}
		
        afterGetTitle = get.getBookByToken(pageNum, pageSize, "title","CassandraTest10");
		afterGetTitle1 = getTitle.getBooksBy(pageNum, pageSize, "CassandraTest10");
		assertEquals(afterGetTitle.get(0).getId(), afterGetTitle1.get(0).getId());
		 
		afterGetAuthor = get.getBookByToken(pageNum, pageSize, "author","Test");
		afterGetAuthor1 = getAuthor.getBooksBy(pageNum, pageSize, "Test");
		assertEquals(afterGetAuthor.get(0).getId(), afterGetAuthor1.get(0).getId());

		afterGetGenre = get.getBookByToken(pageNum, pageSize, "genre","Tester10");
		afterGetGenre1 = getGenre.getBooksBy(pageNum, pageSize, "Tester10");
		assertEquals( afterGetGenre.get(0).getId(),afterGetGenre1.get(0).getId());
		
	}
	
}
