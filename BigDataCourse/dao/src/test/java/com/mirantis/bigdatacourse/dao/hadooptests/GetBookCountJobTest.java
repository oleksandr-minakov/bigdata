package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBooksCountJob;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetBookCountJobTest {

	@Test
	public void testCase() throws DaoException, IOException {
        List<Book> books = new ArrayList<>();
        HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books_dev/", 1);
		GetBooksCountJob count = new GetBooksCountJob(newOne);
		AddBookJob add = new AddBookJob(newOne);
		try {
            for(int i = 0; i < 100; ++i){
                Book book = new Book();
                book.newBook("HadoopTest" + i % 100, "Test", "Tester" + i % 100, new FileInputStream("testbook"));
                add.addBookJob(book);
                books.add(book);
            }
            System.out.println("GetBookCountJobTest " + (count.getBooksCount() == Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory))).size()));
            assertEquals(count.getBooksCount(), Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory))).size());
        } finally {
            for(Book bookForDelete: books)
                newOne.getFS().delete(new Path("/bookshelf/books_dev/" + bookForDelete.getId() + "/"), true);
        }
	}
}
