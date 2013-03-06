package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;

public class GetPageTest {

	@SuppressWarnings("unused")
	@Test
	public void getpagesTest() throws DaoException {
		
		BasicConfigurator.configure();
		List<Book> pagedBookList = new ArrayList<Book>();
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		DaoCassandra dao = new DaoCassandra(cts);
		
		
		Cluster clstr = HFactory.getOrCreateCluster(cts.CLUSTER_NAME, cts.HOST_DEF+":9160");

		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 100; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				dao.addBook(beggining_state);
			}
			assertEquals(dao.getAllBooks(1, 100).size(), cts.bookID);
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (Exception e) {throw new DaoException(e);}
	}
		
}

