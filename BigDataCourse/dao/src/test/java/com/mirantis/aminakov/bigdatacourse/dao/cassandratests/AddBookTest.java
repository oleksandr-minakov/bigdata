package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DAOException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DAOApp;

public class AddBookTest {
	
	@SuppressWarnings("unused")
	@Test
	public void addBookTest(){
		
		BasicConfigurator.configure();
		Cluster clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		DAOApp dao = new DAOApp();
		Book beggining_state = new Book();
		try {
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			dao.addBook(beggining_state);
			System.out.println(beggining_state.getId());
		} catch (FileNotFoundException | DAOException e) {
				e.printStackTrace();
		}
	}
}
