package com.mirantis.aminakov.bigdatacourse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ServiceToDao implements com.mirantis.aminakov.bigdatacourse.service.Service {

    @Autowired
    private Dao dao;

    public static final Logger LOG = Logger.getLogger(ServiceToDao.class);

    public void setDao(Dao dao) {
        this.dao = dao;
    }

	@Override
	public int addBook(Book book) {
		int ret = 0;
		try {
			ret = dao.addBook(book);
		} catch (DaoException e) {
			LOG.debug("Add error:" + e.getMessage());
		}
		return ret;
	}

    @Override
    public int delBook(int id) {
        int result = -1;
        try {
            result = dao.delBook(id);
        } catch (DaoException e) {
            LOG.debug("Delete error:" + e.getMessage());
        }
        return result;
    }

    @Override
	public List<Book> getAllBooks(int pageNum, int pageSize) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getAllBooks(pageNum, pageSize);
		} catch (DaoException e) {
			LOG.debug("Can't get all books:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByAuthor(int pageNum, int pageSize, String author) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByAuthor(pageNum, pageSize, author);
		} catch (DaoException e) {
			LOG.debug("Can't find by author:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByTitle(int pageNum, int pageSize, String title) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByTitle(pageNum, pageSize, title);
		} catch (DaoException e) {
			LOG.debug("Can't find by title:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByText(int pageNum, int pageSize, String text) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByText(pageNum, pageSize, text);
		} catch (DaoException e) {
			LOG.debug("Can't find by text:" + e.getMessage());
		}
		return books;
	}

	@Override
	public List<Book> findByGenre(int pageNum, int pageSize, String genre) {
		List<Book> books = new ArrayList<Book>();
		try {
			books = dao.getBookByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			LOG.debug("Can't find by genre:" + e.getMessage());
		}
		return books;
	}

	@Override
	public TreeSet<String> findAuthorByGenre(int pageNum, int pageSize, String genre) {
		TreeSet<String> authors = new TreeSet<>();
		try {
			authors = dao.getAuthorByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			LOG.debug("Can't find genre by author:" + e.getMessage());
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
