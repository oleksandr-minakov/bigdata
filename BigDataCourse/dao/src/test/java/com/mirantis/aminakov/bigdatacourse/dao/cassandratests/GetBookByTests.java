package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;

public class GetBookByTests {
	
	public Book bstate;
	public Book fstate;

	public void setUp(){
		bstate = new Book();
		fstate = new Book();
	}
	@Test
	public void getBookByTitleTest() throws DaoException{
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP2);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		try {
			for(int i = 0; i < 100; ++i){
				bstate.newBook("CassandraTest" + i, "Test", "Tester", new FileInputStream(BookPath.path));
				dao.addBook(bstate);
			}
			after = dao.getBookByTitle(1, 10, "CassandraTest4");
			for(Book book: after){
				assertTrue(book.getTitle().equals("CassandraTest4"));
				System.out.println(book.getTitle().equals("CassandraTest4"));
			}
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
            System.out.println("!!!");
        }
	}
	@Test
	public void getBookByAuthorTest() throws DaoException{
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		try {
			for(int i = 0; i < 100; ++i){
				bstate.newBook("CassandraTest", "Test" + i, "Tester", new FileInputStream("src/test/java/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByAuthor(1, 10, "Test4");
			for(Book book: after){
				assertTrue(book.getAuthor().equals("Test4"));
				System.out.println(book.getAuthor().equals("Test4"));
			}
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
            System.out.println("!!!");
        }
	}
	@Test
	public void getBookByGenreTest() throws DaoException{
		
		List<Book> after = new ArrayList<Book>();
		setUp();
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		try {
			for(int i = 0; i < 100; ++i){
				bstate.newBook("CassandraTest", "Test", "Tester" + i, new FileInputStream("src/test/java/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getBookByGenre(1, 10, "Tester4");
			for(Book book: after){
				assertTrue(book.getGenre().equals("Tester4"));
				System.out.println(book.getGenre().equals("Tester4"));
			}
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
            System.out.println("!!!");
        }
	}
	
	@Test
	public void getAuthorByGenreTest() throws DaoException{
		
		TreeSet<String> after = new TreeSet<String>();
		setUp();
		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		try {
			for(int i = 0; i < 100; ++i){
				bstate.newBook("CassandraTest", "Test" + i, "Tester" + i, new FileInputStream("src/test/java/testbook"));
				dao.addBook(bstate);
			}
			after = dao.getAuthorByGenre(1, 10, "Tester5");
			for(String book: after){
				assertTrue(book.equals("Test5"));
				System.out.println(book.equals("Test5"));
			}
		} catch (IOException e) {
            throw new DaoException(e);
        } finally {
            cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
            System.out.println("!!!");
        }
	}
}
