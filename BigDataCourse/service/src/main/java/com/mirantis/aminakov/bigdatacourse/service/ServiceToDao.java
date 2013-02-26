package com.mirantis.aminakov.bigdatacourse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.mirantis.aminakov.bigdatacourse.dao.*;
import com.mirantis.aminakov.bigdatacourse.dao.DaoJdbc;

public class ServiceToDao implements Service {
    private Dao dao;

    //TODO Need change to Spring DI
    public ServiceToDao() {
        try {
            dao = new DaoJdbc();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

	@Override
	public int addBook(Book book) {
		int ret = 0;
		try {
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
		try {
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
		try {
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
		try {
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
		try {
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
		try {
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
		try {
			authors = dao.getAuthorByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public int getNumberOfRecords() {
		int numberOfRecords;
		numberOfRecords = dao.getNumberOfRecords();
		return numberOfRecords;
	}
}
