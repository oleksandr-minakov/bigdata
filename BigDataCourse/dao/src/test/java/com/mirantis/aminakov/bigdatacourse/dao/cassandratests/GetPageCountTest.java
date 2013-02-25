package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoApp;

@SuppressWarnings("unused")

public class GetPageCountTest {

	@Test
	public void getPagesCountTest(){
		
		BasicConfigurator.configure();
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		DaoApp dao = new DaoApp(cts);

		List<String> books = new ArrayList<String>();
		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 40; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
				books.add(beggining_state.getGenre());
				dao.addBook(beggining_state);
			}
			assertEquals(dao.getPageCount(books,20), 2);
			
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (FileNotFoundException | DaoException e) {e.printStackTrace();}
	}
}
