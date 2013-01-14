package com.mirantis.aminakov.bigdatacourse;

import java.sql.SQLException;
import java.util.List;

public interface Dao {

	public abstract int addBook(Book book) throws SQLException;

	public abstract List<Book> getAllBooks(int pageNum, int pageSize)
			throws SQLException;

	public abstract List<Book> getBookByTitle(int pageNum, int pageSize,
			String title) throws SQLException;

	public abstract List<Book> getBookByAuthor(int pageNum, int pageSize,
			String author) throws SQLException;

	public abstract List<Book> getBookByGenre(int pageNum, int pageSize,
			String genre) throws SQLException;

	public abstract List<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws SQLException;

}