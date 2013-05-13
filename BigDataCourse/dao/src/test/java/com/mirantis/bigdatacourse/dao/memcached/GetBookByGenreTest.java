package com.mirantis.bigdatacourse.dao.memcached;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import com.mirantis.bigdatacourse.dao.mysql.DaoJdbc;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import static org.junit.Assert.assertTrue;

public class GetBookByGenreTest {

    @SuppressWarnings({ "deprecation", "unused" })
	@Test
    public void getBookByGenreTest() throws DaoException, IOException {
        DaoMemcached daoMemcached = new DaoMemcached();
        daoMemcached.setClient(new MemClient(new InetSocketAddress("localhost" , 11211)));
        DaoJdbc dao = new DaoJdbc();
        DataSource dataSource = new DriverManagerDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bigdata", "aminakov", "bigdata");
        dao.setDataSource(dataSource);
        daoMemcached.setDaoJdbc(dao);
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        PaginationModel model;
        daoMemcached.addBook(book);
        try {
            model = daoMemcached.getBookByGenre(1, 1, "genre");
            assertTrue(model.getBooks().get(0).getGenre().equals(book.getGenre()));
        } finally {
            daoMemcached.delBook(book.getId());
            dao.closeConnection();
        }
    }
}
