package com.mirantis.aminakov.bigdatacourse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.mirantis.aminakov.bigdatacourse.dao.*;

public class ServiceToDao implements Service {

	@Override
	public int addBook(Book book) {
		int ret = 0;
		try {
			Dao dao = new DaoJdbc();
			ret = dao.addBook(book);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize) {
		List<Book> books = new ArrayList<Book>();
		Dao dao;
		try {
			dao = new DaoJdbc();
			books = dao.getAllBooks(pageNum, pageSize);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public List<Book> findByAuthor(int pageNum, int pageSize, String author) {
		List<Book> books = new ArrayList<Book>();
		Dao dao;
		try {
			dao = new DaoJdbc();
			books = dao.getBookByAuthor(pageNum, pageSize, author);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public List<Book> findByTitle(int pageNum, int pageSize, String title) {
		List<Book> books = new ArrayList<Book>();
		Dao dao;
		try {
			dao = new DaoJdbc();
			books = dao.getBookByTitle(pageNum, pageSize, title);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public List<Book> findByText(int pageNum, int pageSize, String text) {
		List<Book> books = new ArrayList<Book>();
		Dao dao;
		try {
			dao = new DaoJdbc();
			books = dao.getBookByText(pageNum, pageSize, text);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public List<Book> findByGenre(int pageNum, int pageSize, String genre) {
		List<Book> books = new ArrayList<Book>();
		Dao dao;
		try {
			dao = new DaoJdbc();
			books = dao.getBookByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public TreeSet<String> findAuthorByGenre(int pageNum, int pageSize, String genre) {
		TreeSet<String> authors = new TreeSet<>();
		Dao dao;
		try {
			dao = new DaoJdbc();
			authors = dao.getAuthorByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public int getNumberOfRecords() {
		int numberOfRecords = 0;
		Dao dao;
		try {
			dao = new DaoJdbc();
			numberOfRecords = dao.getNumberOfRecords();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfRecords;
	}
}
