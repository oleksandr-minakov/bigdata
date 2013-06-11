package com.mirantis.bigdatacourse.dao.memcached;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoMemcached implements Dao {

    @Autowired
    private MemClient memClient;

    @Autowired
    private Dao daoJdbc;

    public void setDaoJdbc(Dao dao) {
        this.daoJdbc = dao;
    }

    public void setMemClient(MemClient memClient) {
        this.memClient = memClient;
    }

    @Override
    public int addBook(Book book) throws DaoException {
        String str = "allBooks";
        int i = str.hashCode();
        memClient.delete(Integer.toString(i));
        return daoJdbc.addBook(book);
    }

    @Override
    public int delBook(String id) throws DaoException {
        String str = "allBooks";
        int i = str.hashCode();
        memClient.delete(Integer.toString(i));
        return daoJdbc.delBook(id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getAllBooks(int pageNum, int pageSize) throws DaoException {
        PaginationModel model;
        String keystring = pageNum + "allBooks" + pageSize;
        model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
        if (model != null ) {                                                         //TODO maybe if(!model.getBooks().isEmpty())
            return model;
        } else {
            model = daoJdbc.getAllBooks(pageNum, pageSize);
            if (!model.getBooks().isEmpty()) {
                memClient.set(Integer.toString(keystring.hashCode()), 60, model);
                model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        PaginationModel model;
        String keystring = pageNum + title + "getBookByTitle" + pageSize;
        model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByTitle(pageNum, pageSize, title);
            if (!model.getBooks().isEmpty()) {
                memClient.set(Integer.toString(keystring.hashCode()), 60, model);
                model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        PaginationModel model;
        String keystring = pageNum + "getBookByText" + text + pageSize;
        model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByText(pageNum, pageSize, text);
            if (!model.getBooks().isEmpty()) {
                memClient.set(Integer.toString(keystring.hashCode()), 60, model);
                model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        PaginationModel model;
        String keystring = pageNum + "getBookByAuthor" + author + pageSize;
        model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByAuthor(pageNum, pageSize, author);
            if (!model.getBooks().isEmpty()) {
                memClient.set(Integer.toString(keystring.hashCode()), 60, model);
                model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        PaginationModel model;
        String keystring = pageNum + "getBookByGenre" + genre + pageSize;
        model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByGenre(pageNum, pageSize, genre);
            if (!model.getBooks().isEmpty()) {
                memClient.set(Integer.toString(keystring.hashCode()), 6, model);
                model = (PaginationModel) memClient.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @Override
    public void closeConnection() throws DaoException {
        daoJdbc.closeConnection();
        memClient.close();
    }

    @Override
    public void afterPropertiesSet() throws DaoException {
        if(this.memClient == null || this.daoJdbc == null) {
            throw new DaoException("Error with memcached bean initialization");
        }
    }

    @Override
    public void destroy() throws Exception {
        this.memClient.close();
        this.daoJdbc.closeConnection();
    }
}
