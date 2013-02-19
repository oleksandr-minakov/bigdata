package com.mirantis.aminakov.bigdatacourse.dao1;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.beans.HColumn;
import org.junit.Test;

public class BookConvTest {

	public Book bstate;
	public Book fstate;
	public List<HColumn<String, String>> cols;
	
	public void setUp(){
		
		bstate = new Book();
		fstate = new Book();
		cols = new ArrayList<HColumn<String, String>>();
	}
	
	@Test
	public void book2rowTest(){
			
		try {
			setUp();
			bstate.newBook(117, "CassandraTest", "Test", "Tester", new FileInputStream("books/testbook"));
			cols = BookConverter.getInstance().book2row(bstate);
			assertNotNull(cols);
			
		} catch (Exception e) {
			e.printStackTrace();}
	}
	
	@Test
	public void row2bookTest(){
		
		setUp();
		try {
			bstate.newBook(117, "CassandraTest", "Test", "Tester", new FileInputStream("resources/testbook"));
			cols = BookConverter.getInstance().book2row(bstate);
			fstate = BookConverter.getInstance().row2book(cols);
			
			System.out.println(bstate.getId() + bstate.getTitle() + bstate.getAuthor() + bstate.getGenre() + bstate.getText());
			System.out.println(fstate.getId() + fstate.getTitle() + fstate.getAuthor() + fstate.getGenre() + fstate.getText());
			assertFalse(bstate.equals(fstate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
