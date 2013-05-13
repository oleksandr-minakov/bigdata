package com.mirantis.bigdatacourse.dao.memcached;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import org.springframework.beans.factory.annotation.Autowired;

public class DaoMemcached implements Dao {
    @Autowired
    private MemClient client;

    @Autowired
    private Dao daoJdbc;

    public void setDaoJdbc(Dao dao) {
        this.daoJdbc = dao;
    }

    public void setClient(MemClient client) {
        this.client = client;
    }

    @Override
    public int addBook(Book book) throws DaoException {
        String str = "allBooks";
        int i = str.hashCode();
        client.delete(Integer.toString(i));
        return daoJdbc.addBook(book);
    }

    @Override
    public int delBook(String id) throws DaoException {
        String str = "allBooks";
        int i = str.hashCode();
        client.delete(Integer.toString(i));
        return daoJdbc.delBook(id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getAllBooks(int pageNum, int pageSize) throws DaoException {
        PaginationModel model = new PaginationModel();
        String keystring = pageNum + "allBooks" + pageSize;
        model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
        if (model != null ) {                                                         //TODO maybe if(!model.getBooks().isEmpty())
            return model;
        } else {
            model = daoJdbc.getAllBooks(pageNum, pageSize);
            if (!model.getBooks().isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, model);
                model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        PaginationModel model = new PaginationModel();
        String keystring = pageNum + title + "getBookByTitle" + pageSize;
        model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByTitle(pageNum, pageSize, title);
            if (!model.getBooks().isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, model);
                model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        PaginationModel model = new PaginationModel();
        String keystring = pageNum + "getBookByText" + text + pageSize;
        model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByText(pageNum, pageSize, text);
            if (!model.getBooks().isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, model);
                model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        PaginationModel model = new PaginationModel();
        String keystring = pageNum + "getBookByAuthor" + author + pageSize;
        model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByAuthor(pageNum, pageSize, author);
            if (!model.getBooks().isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, model);
                model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public PaginationModel getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        PaginationModel model = new PaginationModel();
        String keystring = pageNum + "getBookByGenre" + genre + pageSize;
        model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
        if (model != null) {
            return model;
        } else {
            model = daoJdbc.getBookByGenre(pageNum, pageSize, genre);
            if (!model.getBooks().isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, model);
                model = (PaginationModel) client.get(Integer.toString(keystring.hashCode()));
            }
        }
        return model;
    }

    @Override
    public void closeConnection() throws DaoException {
        daoJdbc.closeConnection();
        client.close();
    }

    @Override
    public void afterPropertiesSet() throws DaoException {
        if(this.client == null || this.daoJdbc == null) {
            throw new DaoException("Error with memcached bean initialization");
        }
    }

    @Override
    public void destroy() throws Exception {
        this.client.close();
        this.daoJdbc.closeConnection();
    }
}
