package com.mirantis.bigdatacourse.dao.memcached;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DaoMemcached implements Dao {
    @Autowired
    private MemClient client;

    @Autowired
    private Dao daoJdbc;

    private int numberOfRecords = -1;

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
    public List<Book> getAllBooks(int pageNum, int pageSize) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + "allBooks" + pageSize;
        String numberOfRecordsKey = keystring + "records";
        books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
        Object o = client.get(Integer.toString(numberOfRecordsKey.hashCode()));
        if (o != null) {
            numberOfRecords = (int) o;
        }
        if (books != null ) {
            return books;
        } else {
            books = daoJdbc.getAllBooks(pageNum, pageSize);
            int records = daoJdbc.getNumberOfRecords();
            if (!books.isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, books);
                books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
                client.set(Integer.toString(numberOfRecordsKey.hashCode()), 6, records);
                numberOfRecords = (int) client.get(Integer.toString(numberOfRecordsKey.hashCode()));
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + title + "getBookByTitle" + pageSize;
        String numberOfRecordsKey = keystring + "records";
        books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
        Object o = client.get(Integer.toString(numberOfRecordsKey.hashCode()));
        if (o != null) {
            numberOfRecords = (int) o;
        }
        if (books != null) {
            return books;
        } else {
            books = daoJdbc.getBookByTitle(pageNum, pageSize, title);
            int records = daoJdbc.getNumberOfRecords();
            if (!books.isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, books);
                books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
                client.set(Integer.toString(numberOfRecordsKey.hashCode()), 6, records);
                numberOfRecords = (int) client.get(Integer.toString(numberOfRecordsKey.hashCode()));
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + "getBookByText" + text + pageSize;
        String numberOfRecordsKey = keystring + "records";
        books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
        Object o = client.get(Integer.toString(numberOfRecordsKey.hashCode()));
        if (o != null) {
            numberOfRecords = (int) o;
        }
        if (books != null) {
            return books;
        } else {
            books = daoJdbc.getBookByText(pageNum, pageSize, text);
            int records = daoJdbc.getNumberOfRecords();
            if (!books.isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, books);
                books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
                client.set(Integer.toString(numberOfRecordsKey.hashCode()), 6, records);
                numberOfRecords = (int) client.get(Integer.toString(numberOfRecordsKey.hashCode()));
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + "getBookByAuthor" + author + pageSize;
        String numberOfRecordsKey = keystring + "records";
        books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
        Object o = client.get(Integer.toString(numberOfRecordsKey.hashCode()));
        if (o != null) {
            numberOfRecords = (int) o;
        }
        if (books != null) {
            return books;
        } else {
            books = daoJdbc.getBookByAuthor(pageNum, pageSize, author);
            int records = daoJdbc.getNumberOfRecords();
            if (!books.isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, books);
                books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
                client.set(Integer.toString(numberOfRecordsKey.hashCode()), 6, records);
                numberOfRecords = (int) client.get(Integer.toString(numberOfRecordsKey.hashCode()));
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + "getBookByGenre" + genre + pageSize;
        String numberOfRecordsKey = keystring + "records";
        books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
        Object o = client.get(Integer.toString(numberOfRecordsKey.hashCode()));
        if (o != null) {
            numberOfRecords = (int) o;
        }
        if (books != null) {
            return books;
        } else {
            books = daoJdbc.getBookByGenre(pageNum, pageSize, genre);
            int records = daoJdbc.getNumberOfRecords();
            if (!books.isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, books);
                books = (List<Book>) client.get(Integer.toString(keystring.hashCode()));
                client.set(Integer.toString(numberOfRecordsKey.hashCode()), 6, records);
                numberOfRecords = (int) client.get(Integer.toString(numberOfRecordsKey.hashCode()));
            }
        }
        return books;
    }

    @Override
    public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        TreeSet<String> authors = new TreeSet<String>();
        String keystring = pageNum + "getAuthorByGenre" + genre + pageSize;
        String numberOfRecordsKey = keystring + "records";
        authors = (TreeSet<String>) client.get(Integer.toString(keystring.hashCode()));
        Object o = client.get(Integer.toString(numberOfRecordsKey.hashCode()));
        if (o != null) {
            numberOfRecords = (int) o;
        }
        if (authors != null) {
            return client.pagination(pageNum, pageSize, authors);
        } else {
            authors = daoJdbc.getAuthorByGenre(pageNum, pageSize, genre);
            int records = daoJdbc.getNumberOfRecords();
            if (!authors.isEmpty()) {
                client.set(Integer.toString(keystring.hashCode()), 6, authors);
                authors = (TreeSet<String>) client.get(Integer.toString(keystring.hashCode()));
                authors = client.pagination(pageNum, pageSize, authors);
                client.set(Integer.toString(numberOfRecordsKey.hashCode()), 6, records);
                numberOfRecords = (int) client.get(Integer.toString(numberOfRecordsKey.hashCode()));
            }
        }
        authors = daoJdbc.getAuthorByGenre(pageNum, pageSize, genre);
        return authors;
    }

    @Override
    public void closeConnection() throws DaoException {
        daoJdbc.closeConnection();
        client.close();
    }

    @Override
    public int getNumberOfRecords() {
        return this.numberOfRecords;
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
