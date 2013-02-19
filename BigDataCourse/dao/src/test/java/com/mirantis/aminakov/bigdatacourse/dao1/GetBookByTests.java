package com.mirantis.aminakov.bigdatacourse.dao1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao1.Book;
import com.mirantis.aminakov.bigdatacourse.dao1.Constants;
import com.mirantis.aminakov.bigdatacourse.dao1.DAOApp;
import com.mirantis.aminakov.bigdatacourse.dao1.DAOException;

public class GetBookByTests {
	
	public Book bstate;
	public Book fstate;
	
	public void setUp(){
		
		bstate = new Book();
		fstate = new Book();
	}
	
	@Test
	public void getBookByTitleTest(){
		
		BasicConfigurator.configure();
		List<Book> after = new ArrayList<Book>();
		setUp();
		Cluster clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		
		if(clstr.describeKeyspace(Constants.KEYSPACE_NAME) != null)
			clstr.dropKeyspace(Constants.KEYSPACE_NAME, true);
		
		DAOApp dao = new DAOApp();
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook(i, new String("CassandraTest" + String.valueOf(i)), "Test", "Tester", new FileInputStream("resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByTitle(1, 40, new String("CassandraTest" + String.valueOf(5)));
			
			for(Book book: after){
				System.out.println(book.getTitle());
			}

			clstr.dropKeyspace(Constants.KEYSPACE_NAME);
		} catch (FileNotFoundException | DAOException e) {e.printStackTrace();}
		
	}
	
	@Test
	public void getBookByAuthorTest(){
		
		
	}
	
	@Test
	public void getBookByGenreTest(){
		
		
	}
	
	@Test
	public void getBookByTextTest(){
		
		
	}
	
}
