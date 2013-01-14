package com.mirantis.aminakov.bigdatacourse;

import java.awt.print.Book;
import java.util.List;

public interface Service {
	public List<Book> findByAuthor();
	public List<Book> findByTitle();
	public List<Book> findByText();
	public List<Book> findByGenre();
	public List<String> findAuthorByGenre();
}
