package com.mirantis.aminakov.bigdatacourse.dao;

import java.util.List;
import java.util.TreeSet;

public interface DAO {

	public int addBook(Book book) throws DAOException;

	public int delBook(int id) throws DAOException;

	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DAOException;

	public List<Book> getBookByTitle(int pageNum, int pageSize,
			String title) throws DAOException;

	public List<Book> getBookByText(int pageNum, int pageSize,
			String text) throws DAOException;

	public List<Book> getBookByAuthor(int pageNum, int pageSize,
			String author) throws DAOException;

	public List<Book> getBookByGenre(int pageNum, int pageSize,
			String genre) throws DAOException;

	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DAOException;

	public void closeConnection() throws DAOException;

}