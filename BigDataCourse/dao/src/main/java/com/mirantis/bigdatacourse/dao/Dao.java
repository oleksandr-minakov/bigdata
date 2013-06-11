package com.mirantis.bigdatacourse.dao;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public interface Dao extends InitializingBean, DisposableBean{

	public int addBook(Book book) throws DaoException;
	
	public int delBook(String id) throws DaoException;

	public PaginationModel getAllBooks(int pageNum, int pageSize)
			throws DaoException;

	public PaginationModel getBookByTitle(int pageNum, int pageSize,
			String title) throws DaoException;
	
	public PaginationModel getBookByText(int pageNum, int pageSize,
			String text) throws DaoException;

	public PaginationModel getBookByAuthor(int pageNum, int pageSize,
			String author) throws DaoException;

	public PaginationModel getBookByGenre(int pageNum, int pageSize,
			String genre) throws DaoException;

	public void closeConnection() throws DaoException;

}