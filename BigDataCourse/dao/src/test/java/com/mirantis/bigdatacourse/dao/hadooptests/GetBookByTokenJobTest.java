package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.*;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetBookByTokenJobTest {

	@Test
	public void test() throws DaoException, IOException {
		
		
		BasicConfigurator.configure();
		int testCase = 100;
		int pageSize = 10000;
		int pageNum = 1;
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books_dev/", 1);
		AddBookJob add = new AddBookJob(newOne);
		GetBookByTokenJob get = new GetBookByTokenJob(newOne);
		GetBookByTitleJob getTitle = new GetBookByTitleJob(newOne);
		GetBookByAuthorJob getAuthor = new GetBookByAuthorJob(newOne);
		GetBookByGenreJob getGenre = new GetBookByGenreJob(newOne);

        List<Book> books = new ArrayList<>();
		List<Book> before = new ArrayList<Book>();
		List<Book> afterGetTitle;
		List<Book> afterGetTitle1;
		List<Book> afterGetGenre;
		List<Book> afterGetGenre1;
		List<Book> afterGetAuthor;
		List<Book> afterGetAuthor1;
        try {
        	
            for(int i = 0; i < testCase; ++i) {
                Book book = new Book();
                book.newBook("HadoopTest10", "Test", "Tester10", new FileInputStream("testbook"));
                before.add(book);
                add.addBookJob(book);
                books.add(book);
            }

            afterGetTitle = get.getBookByToken(pageNum, pageSize, "title","HadoopTest10");
            afterGetTitle1 = getTitle.getBooksBy(pageNum, pageSize, "HadoopTest10");
            assertEquals(afterGetTitle.get(0).getId(), afterGetTitle1.get(0).getId());

            afterGetAuthor = get.getBookByToken(pageNum, pageSize, "author","Test");
            afterGetAuthor1 = getAuthor.getBooksBy(pageNum, pageSize, "Test");
            assertEquals(afterGetAuthor.get(0).getId(), afterGetAuthor1.get(0).getId());

            afterGetGenre = get.getBookByToken(pageNum, pageSize, "genre","Tester10");
            afterGetGenre1 = getGenre.getBooksBy(pageNum, pageSize, "Tester10");
            assertEquals( afterGetGenre.get(0).getId(),afterGetGenre1.get(0).getId());

        } finally {
            for(Book bookForDelete: books)
                newOne.getFS().delete(new Path("/bookshelf/books_dev/" + bookForDelete.getId() + "/"), true);
        }
	}
	
}
