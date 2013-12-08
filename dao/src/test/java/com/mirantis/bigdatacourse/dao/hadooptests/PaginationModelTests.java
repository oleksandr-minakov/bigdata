package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import com.mirantis.bigdatacourse.dao.hadoop.DaoHDFS;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class PaginationModelTests {

	/**
	 * @throws FileNotFoundException 
	 * @throws DaoException 
	 */

	@SuppressWarnings("unused")
	@Test
	public void testCase() throws FileNotFoundException, DaoException {

		try{
			
			HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books_dev/", 1);
			DaoHDFS dao = new DaoHDFS(newOne);
			for(int i = 0; i <100; ++i) {
				
				Book book = new Book();
				book.newBook("HadoopTest"+i, "Test", "Tester", new FileInputStream("testbook"));
				dao.addBook(book);
				
			}
			
			PaginationModel model = dao.getAllBooks(1, 100);
			int a = model.getNumberOfRecords();
			List<Book> books = model.getBooks();
			System.out.println(a);
			
			
		} finally {
			
			
			
		}
	}

}
