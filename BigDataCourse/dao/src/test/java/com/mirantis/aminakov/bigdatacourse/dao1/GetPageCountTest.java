package com.mirantis.aminakov.bigdatacourse.dao1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao1.Book;
import com.mirantis.aminakov.bigdatacourse.dao1.Constants;
import com.mirantis.aminakov.bigdatacourse.dao1.DAOApp;
import com.mirantis.aminakov.bigdatacourse.dao1.DAOException;

public class GetPageCountTest {

	@Test
	public void getPagesCountTest(){
		
		BasicConfigurator.configure();
		Cluster clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		if(clstr.describeKeyspace(Constants.KEYSPACE_NAME) != null)
			clstr.dropKeyspace(Constants.KEYSPACE_NAME, true);
		DAOApp dao = new DAOApp();
		Book beggining_state = new Book();
		try {
			for(int i = 0; i< 40; ++i){
				
				beggining_state.newBook(i, new String("CassandraTest" + String.valueOf(i)), new String("Test" + String.valueOf(i)), new String("Tester" + String.valueOf(i)), new FileInputStream("books/testbook"));
				dao.addBook(beggining_state);
			}
			System.out.println(dao.getPageCount(20));
			
		} catch (FileNotFoundException | DAOException e) {e.printStackTrace();}
	}
}
