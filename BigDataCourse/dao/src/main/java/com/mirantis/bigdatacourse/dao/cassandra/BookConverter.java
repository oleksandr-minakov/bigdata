package com.mirantis.bigdatacourse.dao.cassandra;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.mirantis.bigdatacourse.dao.Book;

import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;

public class BookConverter {

	private static class BookConverterHolder{
		
		private static final BookConverter InstanceHolder = new BookConverter();
	}
	
	public static BookConverter getInstance() {
		
		return BookConverterHolder.InstanceHolder;
	}	

	public List<HColumn<String, String>> book2row(Book newBook) throws IOException {
		
		List<HColumn<String, String>> cols = new ArrayList<HColumn<String, String>>();
		cols.add(HFactory.createColumn("book id", String.valueOf(newBook.getId())));
		cols.add(HFactory.createColumn("book title", newBook.getTitle()));
		cols.add(HFactory.createColumn("book author", newBook.getAuthor()));
		cols.add(HFactory.createColumn("book genre", newBook.getGenre()));
		byte[] toWrap = new byte[newBook.getText().available()];newBook.getText().read(toWrap);
		cols.add(HFactory.createColumn("book text", new String(toWrap)));

		return cols;
	}
	
	public Book row2book(List<HColumn<String, String>> book) throws IOException {
		
		Book newBook = new Book();
		for(HColumn<String, String> col: book) {
			
			if(col.getName().equals("book id")) {
				
				newBook.setId(col.getValue());										}
			
			if(col.getName().equals("book title")) {
				
				newBook.setTitle(col.getValue());													}
			
			if(col.getName().equals("book author")) {
				
				newBook.setAuthor(col.getValue());													}
			
			if(col.getName().equals("book genre")) {
				
				newBook.setGenre(col.getValue());													}
			
			if(col.getName().equals("book text")) {
				
				InputStream is =new ByteArrayInputStream(col.getValue().getBytes("UTF8")); 
				newBook.setText(is);																}
		}
		
		return newBook;
	}
	
}
