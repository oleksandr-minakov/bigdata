package com.mirantis.aminakov.bigdatacourse.dao.memcached;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.mysql.DaoJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
        client.delete("allBooks");
        return daoJdbc.addBook(book);
    }

    @Override
    public int delBook(int id) throws DaoException {
        client.delete("allBooks");
        return daoJdbc.delBook(id);
    }

    @Override
    public List<Book> getAllBooks(int pageNum, int pageSize) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        books = (List<Book>) client.get("allBooks");
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getAllBooks(pageNum, pageSize);
            if (!books.isEmpty()) {
                client.set("allBooks", 6, books);
                books = (List<Book>) client.get("allBooks");
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @Override
    public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        books = (List<Book>) client.get(title);
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByTitle(pageNum, pageSize, title);
            if (!books.isEmpty()) {
                client.set(title, 6, books);
                books = (List<Book>) client.get(title);
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @Override
    public List<Book> getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        books = (List<Book>) client.get(text);
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByText(pageNum, pageSize, text);
            if (!books.isEmpty()) {
                client.set(text, 6, books);
                books = (List<Book>) client.get(text);
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @Override
    public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        books = (List<Book>) client.get(author);
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByAuthor(pageNum, pageSize, author);
            if (!books.isEmpty()) {
                client.set(author, 6, books);
                books = (List<Book>) client.get(author);
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @Override
    public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        books = (List<Book>) client.get(genre);
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByGenre(pageNum, pageSize, genre);
            if (!books.isEmpty()) {
                client.set(genre, 6, books);
                books = (List<Book>) client.get(genre);
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @Override
    public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        TreeSet<String> authors = new TreeSet<String>();
        //Cache conflict with getBookByGenre
        /*authors = (TreeSet<String>) client.get(genre);
        if (authors != null) {
            return client.pagination(pageNum, pageSize, authors);
        } else {
            authors = daoJdbc.getAuthorByGenre(pageNum, pageSize, genre);
            if (!authors.isEmpty()) {
                client.set(genre, 6, authors);
                authors = (TreeSet<String>) client.get(genre);
                authors = client.pagination(pageNum, pageSize, authors);
            }
        }*/
        authors = daoJdbc.getAuthorByGenre(pageNum, pageSize, genre);
        return authors;
    }

    @Override
    public void closeConnection() throws DaoException {
       daoJdbc.closeConnection();
    }

    @Override
    public int getNumberOfRecords() {
        return daoJdbc.getNumberOfRecords();
    }
}
