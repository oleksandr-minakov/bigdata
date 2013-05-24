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

public class GetBookByTextTest {

    @SuppressWarnings({ "deprecation", "unused" })
	@Test
    public void getBookByTextTest() throws DaoException, IOException {
        DaoMemcached daoMemcached = new DaoMemcached();
        daoMemcached.setMemClient(new MemClient(new InetSocketAddress("localhost" , 11211)));
        DaoJdbc dao = new DaoJdbc();
        DataSource dataSource = new DriverManagerDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bigdata", "aminakov", "bigdata");
        dao.setDataSource(dataSource);
        daoMemcached.setDaoJdbc(dao);
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        Book book = new Book();
        PaginationModel model;
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        daoMemcached.addBook(book);
        try {
            model = daoMemcached.getBookByText(1, 1, "teststring");
            String str1 = model.getBooks().get(0).getReadableText();
            String str2 = book.getReadableText();
            assertTrue(str1.equals(str2));
        } finally {
            daoMemcached.delBook(book.getId());
            dao.closeConnection();
        }
    }
}
