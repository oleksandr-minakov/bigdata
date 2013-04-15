package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.NAS.NASMapping;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GetBookByTextTest {

    @Test
    public void getBookByAuthorTest() throws IOException, DaoException, SolrServerException {
        List<Book> books;
        NASMapping daoNAS = new NASMapping("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters("http://localhost:8080/solr-web", daoNAS);
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.server.deleteByQuery("*:*");
        Book book = new Book();
        book.newBook("title", "author", "genre", new ByteArrayInputStream( "edcrfvtgbyhnujmiuytrfde".getBytes()));
        int id = daoSolr.addBook(book);
        try {
            books = daoSolr.getBookByText(1, 1, "miuy");
            assertTrue(books.get(0).getGenre().equals(book.getGenre()));
        } finally {
            daoSolr.delBook(id);
        }
    }
}