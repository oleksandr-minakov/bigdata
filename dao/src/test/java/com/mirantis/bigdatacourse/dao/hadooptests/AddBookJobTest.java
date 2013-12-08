package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddBookJobTest {
	@Test
	public void addBookTest()throws DaoException, IOException {
        HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books_dev/", 1);
        List<Book> books = new ArrayList<Book>();
        try {
            Book book = new Book();
            book.newBook("HadoopTest", "Test", "Tester", new FileInputStream("testbook"));
            books.add(book);
            int res = new AddBookJob(newOne).addBookJob(book);
            System.out.println("AddBookJobTest. Id =  " + book.getId());
            assertEquals(0, res);
        } finally {
            for (Book bookForDelete: books) {
                newOne.getFS().delete(new Path("/bookshelf/books_dev/" + bookForDelete.getId() + "/"), true);
            }
        }
    }
}
