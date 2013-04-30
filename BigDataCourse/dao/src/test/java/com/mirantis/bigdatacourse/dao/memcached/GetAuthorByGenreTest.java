package com.mirantis.bigdatacourse.dao.memcached;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.mysql.DaoJdbc;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;

public class GetAuthorByGenreTest {

    @Test
    public void getAuthorByGenreTest() throws DaoException, IOException {
        DaoMemcached daoMemcached = new DaoMemcached();
        daoMemcached.setClient(new MemClient(new InetSocketAddress("localhost" , 11211)));
        DaoJdbc dao = new DaoJdbc();
        DataSource dataSource = new DriverManagerDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bigdata", "aminakov", "bigdata");
        dao.setDataSource(dataSource);
        daoMemcached.setDaoJdbc(dao);
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        Book book = new Book();
        Book book2 = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        book2.newBook("title2", "author2", "genre", new FileInputStream("testbook"));
        TreeSet<String> authors = new TreeSet<String>();
        daoMemcached.addBook(book);
         daoMemcached.addBook(book2);
        try {
            authors = daoMemcached.getAuthorByGenre(1, 2, "genre");
            assertTrue(authors.size() == 2);
        } finally {
            daoMemcached.delBook(book.getId());
            daoMemcached.delBook(book2.getId());
            dao.closeConnection();
        }
    }
}
