package com.mirantis.bigdatacourse.service.restful;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;

public class RESTtoDao implements RESTservice {

	@Autowired
    private Dao dao;

    public static final Logger LOG = Logger.getLogger(RESTtoDao.class);

    public void setDao(Dao dao) {
        this.dao = dao;
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
    public int delBook(int id) {
        int result = -1;
        try {
            result = dao.delBook(id);
        } catch (DaoException e) {
            LOG.debug("Delete error:" + e.getMessage());
        }
        return result;
    }

}
