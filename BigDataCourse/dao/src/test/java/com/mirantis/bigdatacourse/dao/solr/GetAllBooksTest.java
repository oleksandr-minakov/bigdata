package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.NAS.NASMapping;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GetAllBooksTest {

    @Test
    public void getAllBooksTest() throws DaoException, IOException, SolrServerException {
        List<Book> books;
        PaginationModel model;
        String url = "http://0.0.0.0:8081/solr-web";
        DaoSolr daoSolr = new DaoSolr(url);
        daoSolr.setNASMapping(new NASMapping("/tmp/solr_nas_dev/", 3));
        daoSolr.getServer().deleteByQuery("*:*");
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        daoSolr.addBook(book);
        try {
            model = daoSolr.getAllBooks(1, 1);
            books = model.getBooks();
            assertTrue(books.size() == 1);
        } finally {
            daoSolr.delBook(book.getId());
        }
    }
}
