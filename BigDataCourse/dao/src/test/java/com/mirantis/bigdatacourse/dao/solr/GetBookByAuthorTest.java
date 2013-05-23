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
        NASMapping daoNAS = new NASMapping("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters();
        parameters.URL = "http://0.0.0.0:8080/solr-web";
        parameters.daoNAS = daoNAS;
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.getServer().deleteByQuery("*:*");
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
