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

public class GetBookByAuthorTest {

    @Test
    public void getBookByAuthorTest() throws IOException, DaoException, SolrServerException {
        List<Book> books;
        PaginationModel model;
        String url = "http://0.0.0.0:8081/solr-web";
        DaoSolr daoSolr = new DaoSolr(url);
        daoSolr.getServer().deleteByQuery("*:*");
        daoSolr.setNASMapping(new NASMapping("/tmp/solr_nas_dev/", 3));
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        daoSolr.addBook(book);
        try {
            model = daoSolr.getBookByAuthor(1, 1, "author");
            books = model.getBooks();
            assertTrue(books.get(0).getAuthor().equals(book.getAuthor()));
        } finally {
            daoSolr.delBook(book.getId());
        }
    }
}
