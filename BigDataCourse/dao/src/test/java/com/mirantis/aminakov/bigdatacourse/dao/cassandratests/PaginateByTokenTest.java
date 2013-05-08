package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PaginateByTokenTest {

	@SuppressWarnings("unused")
	@Test
	public void paginationTest() throws DaoException, InterruptedException {
		
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		Book initial_state = new Book();
		for(int i = 0; i < 500; ++i){
            try {
                initial_state.newBook("CassandraTest" + i % 250, "Test" + i % 2, "Tester" + i % 50, new FileInputStream(BookPath.path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            dao.addBook(initial_state);
		}
		List<Book> lst1 = dao.getBooksByToken("Test0", "book author");
		int querySize_lst1 = dao.getNumberOfRecords();
		List<Book> lst2 = dao.getBooksByToken("Test1", "book author");
		int querySize_lst2 = dao.getNumberOfRecords();
		List<Book> lst3 = dao.getBooksByToken("CassandraTest0", "book title");
		int querySize_lst3 = dao.getNumberOfRecords();
		List<Book> lst4 = dao.getBooksByToken("Tester1", "book genre");
		int querySize_lst4 = dao.getNumberOfRecords();
        cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
        
		System.out.println((lst1.size() + lst2.size() + lst3.size() + lst4.size()) != 0);
        assertTrue((lst1.size() + lst2.size() + lst3.size() + lst4.size()) != 0);
	}
}
