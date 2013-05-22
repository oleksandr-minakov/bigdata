package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetAllBooksJob;
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
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books_dev/", 1);
		AddBookJob add = new AddBookJob(newOne);
		GetAllBooksJob get = new GetAllBooksJob(newOne);
		List<Book> booksBefore = new ArrayList<>();
		List<Book> booksAfter = null;
        try {
            for(int i = 0; i < 10; ++i) {
                Book book = new Book();
                book.newBook("HadoopTest" + i, "Test" + i, "Tester" + i, new FileInputStream("testbook"));
                booksBefore.add(book);
                add.addBookJob(book);
            }
            booksAfter = get.getAllBooksJob(1, 10);
            System.out.println("GetAllBooksJobTest " + ( booksBefore.size() == booksAfter.size() ));
            assertEquals(booksBefore.size(), booksAfter.size());
        } finally {
            if (booksAfter != null) {
                for(Book bookForDelete: booksAfter) {
                    newOne.getFS().delete(new Path("/bookshelf/books_dev/" + bookForDelete.getId() + "/"), true);
                }
            }
        }
	}
}
