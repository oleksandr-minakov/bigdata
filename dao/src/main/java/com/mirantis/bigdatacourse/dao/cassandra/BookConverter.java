package com.mirantis.bigdatacourse.dao.cassandra;

import com.mirantis.bigdatacourse.dao.Book;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookConverter {

	private static class BookConverterHolder{
		private static final BookConverter InstanceHolder = new BookConverter();
	}
	
	public static BookConverter getInstance() {
		return BookConverterHolder.InstanceHolder;
	}	

	public List<HColumn<String, String>> book2row(Book newBook) throws IOException {
		List<HColumn<String, String>> hColumns = new ArrayList<>();
		hColumns.add(HFactory.createColumn("book id", String.valueOf(newBook.getId())));
		hColumns.add(HFactory.createColumn("book title", newBook.getTitle()));
		hColumns.add(HFactory.createColumn("book author", newBook.getAuthor()));
		hColumns.add(HFactory.createColumn("book genre", newBook.getGenre()));
		byte[] toWrap = new byte[newBook.getText().available()];newBook.getText().read(toWrap);
		hColumns.add(HFactory.createColumn("book text", new String(toWrap)));
		return hColumns;
	}
	
	public Book row2book(List<HColumn<String, String>> book) throws IOException {
		Book newBook = new Book();
		for(HColumn<String, String> columns : book) {
			if(columns.getName().equals("book id"))
				newBook.setId(columns.getValue());

			if(columns.getName().equals("book title"))
				newBook.setTitle(columns.getValue());

			if(columns.getName().equals("book author"))
				newBook.setAuthor(columns.getValue());

			if(columns.getName().equals("book genre"))
				newBook.setGenre(columns.getValue());
			
			if(columns.getName().equals("book text")) {
				InputStream is = new ByteArrayInputStream(columns.getValue().getBytes("UTF8"));
				newBook.setText(is);
            }
		}
		return newBook;
	}
}
