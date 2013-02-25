package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DAOApp;

public class GetAllRowKeysTest {
	
	@Test
	public void getIterList(){
		
		BasicConfigurator.configure();
		
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		
		DAOApp dao = new DAOApp(cts);
		
		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 40; ++i){
				
				beggining_state.newBook(new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("src/main/resources/testbook"));
			}
			List<String> keys = dao.getAllRowKeys();
			for(String key: keys){
				System.out.println(key);
			}
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}

}
