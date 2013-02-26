package com.mirantis.aminakov.bigdatacourse.dao.cassandra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;

public class Main {

	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) throws FileNotFoundException, DaoException {

//		BasicConfigurator.configure();
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/DAOConfig.xml");
		
		Constants cts = (Constants) ctx.getBean("cassandra state");
		
		DaoApp dao = (DaoApp) ctx.getBean("DAOCassandra");
		
		Book beggining_state = new Book();
		List<Book> before = new ArrayList<Book>();
		List<Book> after = new ArrayList<Book>();
		for(int i = 0; i< 10; ++i){
			beggining_state.newBook(new String("CassandraTest" + 100 % (i+1) ), "Test" + i  , "Tester", new FileInputStream("src/main/resources/testbook"));
			before.add(beggining_state);
			dao.addBook(beggining_state);
		}
		
		List<Book> lst = dao.getBooksByToken("CassandraTest4","book title");

		cts.getCurrentClstr().dropKeyspace(cts.KEYSPACE_NAME);
	}

}
