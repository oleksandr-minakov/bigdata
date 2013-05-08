package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandratests.BookPath;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByAuthorJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByGenreJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByTokenJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByTitleJob;

public class GetBookByTokenJobTest {

	@Test
	public void test() throws DaoException, IOException {
		
		int testCase = 1000;
		int pageSize = 10000;
		int pageNum = 1;
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.bookID = 1;
		AddBookJob add = new AddBookJob(newOne);
		GetBookByTokenJob get = new GetBookByTokenJob(newOne);
		GetBookByTitleJob getTitle = new GetBookByTitleJob(newOne);
		GetBookByAuthorJob getAuthor = new GetBookByAuthorJob(newOne);
		GetBookByGenreJob getGenre = new GetBookByGenreJob(newOne);
		
		
		List<Book> before = new ArrayList<Book>();
		List<Book> afterGetTitle = new ArrayList<Book>();
		List<Book> afterGetTitle1 = new ArrayList<Book>();
		List<Book> afterGetGenre = new ArrayList<Book>();
		List<Book> afterGetGenre1 = new ArrayList<Book>();
		List<Book> afterGetAuthor = new ArrayList<Book>();
		List<Book> afterGetAuthor1 = new ArrayList<Book>();
		
		for(int i=0; i< testCase; ++i){
			 
			Book beggining_state = new Book();
			beggining_state.newBook("CassandraTest" + i%100, "Test", "Tester"+i%100, new FileInputStream(BookPath.path));
			before.add(beggining_state);
			add.addBookJob(beggining_state);
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
		
		 System.out.println("GetBookByTokenJobTest " + (afterGetTitle.get(0).getId() + afterGetAuthor.get(0).getId() +  afterGetGenre.get(0).getId() 
				 == afterGetTitle1.get(0).getId() + afterGetAuthor1.get(0).getId() +  afterGetGenre1.get(0).getId()));
		
		 
		for(int i = 1; i< testCase+1; ++i)
			newOne.getFS().delete(new Path("/bookshelf/books/"+i+"/"), true);
	}

}
