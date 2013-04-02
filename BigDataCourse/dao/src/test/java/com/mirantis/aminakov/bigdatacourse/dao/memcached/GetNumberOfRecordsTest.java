package com.mirantis.aminakov.bigdatacourse.dao.memcached;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.mysql.DaoJdbc;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class GetNumberOfRecordsTest {

    @Test
    public void getNumberOfRecordsTest() throws IOException, DaoException {
        DaoMemcached daoMemcached = new DaoMemcached();
        daoMemcached.setClient(new MemClient(new InetSocketAddress("0.0.0.0" , 11211)));
        DaoJdbc dao = new DaoJdbc();
        DataSource dataSource = new DriverManagerDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://0.0.0.0:3306/bigdata", "aminakov", "bigdata");
        dao.setDataSource(dataSource);
        daoMemcached.setDaoJdbc(dao);
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("0.0.0.0", 11211));
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        int id = daoMemcached.addBook(book);
        List<Book> books = new ArrayList<Book>();
        books = daoMemcached.getBookByText(1, 1, "hisdu");
        int records = daoMemcached.getNumberOfRecords();
        assertTrue(records == 1);
        daoMemcached.delBook(id);
    }
}