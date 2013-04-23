package com.mirantis.bigdatacourse.dao.memcached;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.memcached.DaoMemcached;
import com.mirantis.bigdatacourse.dao.memcached.MemClient;
import com.mirantis.bigdatacourse.dao.mysql.DaoJdbc;

import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class GetNumberOfRecordsTest {

    @SuppressWarnings({ "deprecation", "unused" })
	@Test
    public void getNumberOfRecordsTest() throws IOException, DaoException {
        DaoMemcached daoMemcached = new DaoMemcached();
        daoMemcached.setClient(new MemClient(new InetSocketAddress("localhost" , 11211)));
        DaoJdbc dao = new DaoJdbc();
        DataSource dataSource = new DriverManagerDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bigdata", "aminakov", "bigdata");
        dao.setDataSource(dataSource);
        daoMemcached.setDaoJdbc(dao);
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        List<Book> books = new ArrayList<Book>();
        String str = 1 + 1 + "teststr";
        int i = str.hashCode();
        client.delete(Integer.toString(i));
        int id = daoMemcached.addBook(book);
        try {
            books = daoMemcached.getBookByText(1, 1, "teststr");
            System.out.println(books.size());
            int records = daoMemcached.getNumberOfRecords();
            System.out.println(records);
            assertTrue(records == 1);
        } finally {
            daoMemcached.delBook(id);
            dao.closeConnection();
        }
    }
}
