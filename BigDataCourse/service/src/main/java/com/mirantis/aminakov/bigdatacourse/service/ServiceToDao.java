package com.mirantis.aminakov.bigdatacourse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.*;

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

	public static final Logger LOG = Logger.getLogger(ServiceToDao.class);
	
	@Override
	public int addBook(Book book) {
		int ret = 0;
		try {
			ret = dao.addBook(book);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());
		}
		return ret;
	}

    @Override
    public int delBook(int id) {
        int result = -1;
        try {
            result = dao.delBook(id);
        } catch (DaoException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    @Override
	public List<Book> getAllBooks(int pageNum, int pageSize) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getAllBooks(pageNum, pageSize);
		} catch (DaoException e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByAuthor(int pageNum, int pageSize, String author) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByAuthor(pageNum, pageSize, author);
		} catch (DaoException e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByTitle(int pageNum, int pageSize, String title) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByTitle(pageNum, pageSize, title);
		} catch (DaoException e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByText(int pageNum, int pageSize, String text) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByText(pageNum, pageSize, text);
		} catch (DaoException e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByGenre(int pageNum, int pageSize, String genre) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());
		}
		return books;
	}

	@Override
	public TreeSet<String> findAuthorByGenre(int pageNum, int pageSize, String genre) {
		TreeSet<String> authors = new TreeSet<>();
		try {
			authors = dao.getAuthorByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());
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
