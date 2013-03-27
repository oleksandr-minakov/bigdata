package com.mirantis.aminakov.bigdatacourse.dao.memcached;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;

import static org.junit.Assert.assertTrue;

public class GetKeyTest {

    @Test
    public void getKeyTest() throws IOException {
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("0.0.0.0" , 11211));
        Book book = new Book();
        Book book1 = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        client.set("testBook", 5, book);
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        book1 = (Book) client.get("testBook");
        assertTrue(book.getTitle().equals(book1.getTitle()) &&
                   book.getAuthor().equals(book1.getAuthor()) &&
                   book.getGenre().equals(book1.getGenre()) &&
                   book.getReadableText().equals(book1.getReadableText()));
    }
}
