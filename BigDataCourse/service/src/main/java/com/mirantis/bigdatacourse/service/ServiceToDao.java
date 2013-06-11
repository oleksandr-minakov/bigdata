package com.mirantis.bigdatacourse.service;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceToDao implements com.mirantis.bigdatacourse.service.Service {

    @Autowired
    private Dao dao;

    public static final Logger LOG = Logger.getLogger(ServiceToDao.class);

    public void setDao(Dao dao) {
        this.dao = dao;
    }

	@Override
	public int addBook(Book book) {
		int ret = -1;
		try {
			ret = dao.addBook(book);
		} catch (DaoException e) {
			LOG.debug("Add error:" + e.getMessage());
		}
		return ret;
	}

    @Override
    public int delBook(String id) {
        int result = -1;
        try {
            result = dao.delBook(id);
        } catch (DaoException e) {
            LOG.debug("Delete error:" + e.getMessage());
        }
        return result;
    }

    @Override
	public PaginationModel getAllBooks(int pageNum, int pageSize) {
        PaginationModel model = new PaginationModel();
        try {
            model = dao.getAllBooks(pageNum, pageSize);
        } catch (DaoException e) {
            LOG.debug("Can't get all books:" + e.getMessage());
        }
        return model;
    }

	@Override
	public PaginationModel findByAuthor(int pageNum, int pageSize, String author) {
        PaginationModel model = new PaginationModel();
		try {
			model = dao.getBookByAuthor(pageNum, pageSize, author);
		} catch (DaoException e) {
			LOG.debug("Can't find by author:" + e.getMessage());
		}
		return model;
	}

	@Override
	public PaginationModel findByTitle(int pageNum, int pageSize, String title) {
        PaginationModel model = new PaginationModel();
		try {
			model = dao.getBookByTitle(pageNum, pageSize, title);
		} catch (DaoException e) {
			LOG.debug("Can't find by title:" + e.getMessage());
		}
		return model;
	}

	@Override
	public PaginationModel findByText(int pageNum, int pageSize, String text) {
        PaginationModel model = new PaginationModel();
		try {
			model = dao.getBookByText(pageNum, pageSize, text);
		} catch (DaoException e) {
			LOG.debug("Can't find by text:" + e.getMessage());
		}
		return model;
	}

	@Override
	public PaginationModel findByGenre(int pageNum, int pageSize, String genre) {
        PaginationModel model = new PaginationModel();
		try {
			model = dao.getBookByGenre(pageNum, pageSize, genre);
		} catch (DaoException e) {
			LOG.debug("Can't find by genre:" + e.getMessage());
		}
		return model;
	}
}
