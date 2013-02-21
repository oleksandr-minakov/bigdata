package com.mirantis.aminakov.bigdatacourse.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.DAOApp;
import com.mirantis.aminakov.bigdatacourse.dao.DAOException;

public class GetBookByTests {
	
	public Book bstate;
	public Book fstate;
	
	public void setUp(){
		
		bstate = new Book();
		fstate = new Book();
	}
	@Test
	public void getBookByTitleTest(){
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		Cluster clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		
		if(clstr.describeKeyspace(Constants.KEYSPACE_NAME) != null)
			clstr.dropKeyspace(Constants.KEYSPACE_NAME, true);
		
		DAOApp dao = new DAOApp();
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest"+i, "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByTitle(1, 10, new String("CassandraTest4"));
			
			for(Book book: after){
				System.out.println(book.getTitle());
			}

			clstr.dropKeyspace(Constants.KEYSPACE_NAME);
		} catch (FileNotFoundException | DAOException e) {e.printStackTrace();}
		
	}
	
	@Test
	public void getBookByAuthorTest(){
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		Cluster clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		
		if(clstr.describeKeyspace(Constants.KEYSPACE_NAME) != null)
			clstr.dropKeyspace(Constants.KEYSPACE_NAME, true);
		
		DAOApp dao = new DAOApp();
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest", "Test"+i, "Tester", new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByAuthor(1, 10, new String("Test4"));
			
			for(Book book: after){
				System.out.println(book.getAuthor());
			}

			clstr.dropKeyspace(Constants.KEYSPACE_NAME);
		} catch (FileNotFoundException | DAOException e) {e.printStackTrace();}
		
	}
	
	@Test
	public void getBookByGenreTest(){
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		Cluster clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		
		if(clstr.describeKeyspace(Constants.KEYSPACE_NAME) != null)
			clstr.dropKeyspace(Constants.KEYSPACE_NAME, true);
		
		DAOApp dao = new DAOApp();
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest", "Test", "Tester"+i, new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByGenre(1, 10, new String("Tester4"));
			
			for(Book book: after){
				System.out.println(book.getAuthor());
			}

			clstr.dropKeyspace(Constants.KEYSPACE_NAME);
		} catch (FileNotFoundException | DAOException e) {e.printStackTrace();}
		
	}
	
	@Test
	public void getAythorByGenreTest(){
		
		TreeSet<String> after = new TreeSet<String>();
		setUp();
		Cluster clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		
		if(clstr.describeKeyspace(Constants.KEYSPACE_NAME) != null)
			clstr.dropKeyspace(Constants.KEYSPACE_NAME, true);
		
		DAOApp dao = new DAOApp();
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest", "Test"+i, "Tester"+i, new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getAuthorByGenre(1, 10, new String("Tester5"));
			
			for(String book: after){
				System.out.println(book);
			}

			clstr.dropKeyspace(Constants.KEYSPACE_NAME);
		} catch (FileNotFoundException | DAOException e) {e.printStackTrace();}
		
	}
	
}
