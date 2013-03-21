package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class PaginateByTokenTest {

	@Test
	public void paginationTest() throws DaoException, FileNotFoundException{
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		
		DaoCassandra dao = new DaoCassandra(cts);
		Book beggining_state = new Book();
		
		for(int i = 0; i< 500; ++i){
			beggining_state.newBook("CassandraTest"+i%250, "Test" + i%2  , "Tester"+i%50, new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
		}
		
		List<Book> lst1 = dao.getBooksByToken("Test0","book author");
		int querySize_lst1 = dao.getNumberOfRecords();
		
		List<Book> lst2 = dao.getBooksByToken("Test1","book author");
		int querySize_lst2 = dao.getNumberOfRecords();
		
		List<Book> lst3 = dao.getBooksByToken("CassandraTest0","book title");
		int querySize_lst3 = dao.getNumberOfRecords();
		
		List<Book> lst4 = dao.getBooksByToken("Tester1","book genre");
		int querySize_lst4 = dao.getNumberOfRecords();
		
		cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		
		System.out.println((lst1.size() + lst2.size() + lst3.size() + lst4.size()) == (querySize_lst1 + querySize_lst2 + querySize_lst3 + querySize_lst4));
		
		assertEquals(lst1.size() + lst2.size() + lst3.size() + lst4.size(), querySize_lst1 + querySize_lst2 + querySize_lst3 + querySize_lst4);
	}

}
