package com.mirantis.bigdatacourse.service.restful;

import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class RestToDao implements RestService {

	@Autowired
    private Dao dao;

    public static final Logger LOG = Logger.getLogger(RestToDao.class);

    public void setDao(Dao dao) {
        this.dao = dao;
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
}
