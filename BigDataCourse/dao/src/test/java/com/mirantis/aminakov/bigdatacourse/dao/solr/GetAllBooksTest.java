package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GetAllBooksTest {

    @Test
    public void getAllBooksTest() throws DaoException, FileNotFoundException {
        List<Book> books = new ArrayList<Book>();
        Parameters parameters = new Parameters("http://localhost:8080/solr-web");
        DaoSolr daoSolr = new DaoSolr(parameters);
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        int id = daoSolr.addBook(book);
        try {
            books = daoSolr.getAllBooks(1, 1);
            assertTrue(books.size() == 1);
        } finally {
            daoSolr.delBook(id);
        }
    }
}
