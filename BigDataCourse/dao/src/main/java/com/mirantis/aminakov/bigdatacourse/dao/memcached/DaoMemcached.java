package com.mirantis.aminakov.bigdatacourse.dao.memcached;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
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
        String str = "allBooks";
        int i = str.hashCode();
        client.delete(Integer.toString(i));
        return daoJdbc.addBook(book);
    }

    @Override
    public int delBook(int id) throws DaoException {
        String str = "allBooks";
        int i = str.hashCode();
        client.delete(Integer.toString(i));
        return daoJdbc.delBook(id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getAllBooks(int pageNum, int pageSize) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + pageSize + "allBooks";
        int hash = keystring.hashCode();
        books = (List<Book>) client.get(Integer.toString(hash));
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getAllBooks(pageNum, pageSize);
            if (!books.isEmpty()) {
                client.set(Integer.toString(hash), 60, books);
                books = (List<Book>) client.get(Integer.toString(hash));
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + pageSize + title;
        int hash = keystring.hashCode();
        books = (List<Book>) client.get(Integer.toString(hash));
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByTitle(pageNum, pageSize, title);
            if (!books.isEmpty()) {
                client.set(Integer.toString(hash), 60, books);
                books = (List<Book>) client.get(Integer.toString(hash));
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + pageSize + "getBookByText" + text;
        int hash = keystring.hashCode();
        books = (List<Book>) client.get(Integer.toString(hash));
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByText(pageNum, pageSize, text);
            if (!books.isEmpty()) {
                client.set(Integer.toString(hash), 60, books);
                books = (List<Book>) client.get(Integer.toString(hash));
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + pageSize + "getBookByAuthor" + author;
        int hash = keystring.hashCode();
        books = (List<Book>) client.get(Integer.toString(hash));
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByAuthor(pageNum, pageSize, author);
            if (!books.isEmpty()) {
                client.set(Integer.toString(hash), 60, books);
                books = (List<Book>) client.get(Integer.toString(hash));
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        String keystring = pageNum + pageSize + "getBookByGenre" + genre;
        int hash = keystring.hashCode();
        books = (List<Book>) client.get(Integer.toString(hash));
        if (books != null) {
            return client.pagination(pageNum, pageSize, books);
        } else {
            books = daoJdbc.getBookByGenre(pageNum, pageSize, genre);
            if (!books.isEmpty()) {
                client.set(Integer.toString(hash), 60, books);
                books = (List<Book>) client.get(Integer.toString(hash));
                books = client.pagination(pageNum, pageSize, books);
            }
        }
        return books;
    }

    @Override
    public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        TreeSet<String> authors = new TreeSet<String>();
        //Cache conflict with getBookByGenre
        String keystring = pageNum + pageSize + "getAuthorByGenre" + genre;
        int hash = keystring.hashCode();
        authors = (TreeSet<String>) client.get(Integer.toString(hash));
        if (authors != null) {
            return client.pagination(pageNum, pageSize, authors);
        } else {
            authors = daoJdbc.getAuthorByGenre(pageNum, pageSize, genre);
            if (!authors.isEmpty()) {
                client.set(Integer.toString(hash), 60, authors);
                authors = (TreeSet<String>) client.get(Integer.toString(hash));
                authors = client.pagination(pageNum, pageSize, authors);
            }
        }
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

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws Exception {
		this.daoJdbc.closeConnection();
		this.daoJdbc = null;
		
	}
}
