package com.mirantis.aminakov.bigdatacourse.dao.memcached;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.mysql.DaoJdbc;
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

public class GetAllBooksTest {

    @SuppressWarnings({ "deprecation", "unchecked" })
	@Test
    public void getAllBooksTest() throws DaoException, IOException {
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
        List<Book> resultList = new ArrayList<Book>();
        int id = daoMemcached.addBook(book);
        try {
            books = daoMemcached.getAllBooks(1,1);
            String str = 1 + "allBooks" + 1;
            int i = str.hashCode();
            resultList = (List<Book>) client.get(Integer.toString(i));
            assertTrue(books.size() == resultList.size());
        } finally {
            daoMemcached.delBook(id);
            dao.closeConnection();
        }
    }
}
