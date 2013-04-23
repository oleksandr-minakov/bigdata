package com.mirantis.bigdatacourse.dao;

import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public interface Dao extends InitializingBean, DisposableBean{

	public int addBook(Book book) throws DaoException;
	
	public int delBook(int id) throws DaoException;

	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DaoException;

	public List<Book> getBookByTitle(int pageNum, int pageSize,
			String title) throws DaoException;
	
	public List<Book> getBookByText(int pageNum, int pageSize,
			String text) throws DaoException;

	public List<Book> getBookByAuthor(int pageNum, int pageSize,
			String author) throws DaoException;

	public List<Book> getBookByGenre(int pageNum, int pageSize,
			String genre) throws DaoException;

	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException;
	
	public void closeConnection() throws DaoException;
	
	public int getNumberOfRecords();

}