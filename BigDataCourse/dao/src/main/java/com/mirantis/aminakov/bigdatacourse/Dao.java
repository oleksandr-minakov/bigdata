package com.mirantis.aminakov.bigdatacourse;

import java.util.List;

public interface Dao {

	public int addBook(Book book) throws DaoException;

	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DaoException;

	public List<Book> getBookByTitle(int pageNum, int pageSize,
			String title) throws DaoException;

	public List<Book> getBookByAuthor(int pageNum, int pageSize,
			String author) throws DaoException;

	public List<Book> getBookByGenre(int pageNum, int pageSize,
			String genre) throws DaoException;

	public List<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException;

}