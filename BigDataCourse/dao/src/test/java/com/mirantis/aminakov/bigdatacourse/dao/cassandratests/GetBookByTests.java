package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoApp;
@SuppressWarnings("unused")

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
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		Dao dao = new DaoApp(cts);
		
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest"+i, "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByTitle(1, 10, new String("CassandraTest4"));
			
			for(Book book: after){
				assertTrue(book.getTitle().equals(new String("CassandraTest4")));
				System.out.println(book.getTitle().equals(new String("CassandraTest4")));
			}
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (FileNotFoundException | DaoException e) {e.printStackTrace();}
		
	}
	
	@Test
	public void getBookByAuthorTest(){
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		Dao dao = new DaoApp(cts);
		
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest", "Test"+i, "Tester", new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByAuthor(1, 10, new String("Test4"));
			
			for(Book book: after){
				assertTrue(book.getAuthor().equals(new String("Test4")));
				System.out.println(book.getAuthor().equals(new String("Test4")));
			}
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (FileNotFoundException | DaoException e) {e.printStackTrace();}
		
	}
	
	@Test
	public void getBookByGenreTest(){
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		Dao dao = new DaoApp(cts);
		 
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest", "Test", "Tester"+i, new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByGenre(1, 10, new String("Tester4"));
			
			for(Book book: after){
				assertTrue(book.getGenre().equals(new String("Tester4")));
				System.out.println(book.getGenre().equals(new String("Tester4")));
			}
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (FileNotFoundException | DaoException e) {e.printStackTrace();}
		
	}
	

	@Test
	public void getAythorByGenreTest(){
		
		TreeSet<String> after = new TreeSet<String>();
		setUp();
		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", "localhost");
		Dao dao = new DaoApp(cts);
		
		try {
			for(int i = 0; i< 10; ++i){
				
				bstate.newBook("CassandraTest", "Test"+i, "Tester"+i, new FileInputStream("src/main/resources/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getAuthorByGenre(1, 10, new String("Tester5"));
			
			for(String book: after){
				assertTrue(book.equals(new String("Test5")));
				System.out.println(book.equals(new String("Test5")));
			}
			cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
		} catch (FileNotFoundException | DaoException e) {e.printStackTrace();}
		
	}
	
}
