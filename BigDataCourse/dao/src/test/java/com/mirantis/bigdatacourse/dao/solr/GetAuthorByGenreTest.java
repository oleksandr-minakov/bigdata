package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.NAS.NASMapping;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;

public class GetAuthorByGenreTest {

    @Test
    public void getAuthorByGenreTest() throws IOException, DaoException, SolrServerException {
        TreeSet<String> authors;
        List<Book> books = new ArrayList<Book>();
        NASMapping daoNAS = new NASMapping("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters();
        parameters.URL = "http://0.0.0.0:8080/solr-web";
        parameters.daoNAS = daoNAS;
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.getServer().deleteByQuery("*:*");
        Book book = new Book();
        for (int i = 1; i < 10; i++) {
            book.newBook("title" + i, "author" + i, "genre", new FileInputStream("testbook"));
            daoSolr.addBook(book);
            books.add(book);
        }
        try {
            authors = daoSolr.getAuthorByGenre(1, 10, "genre");
            assertTrue(authors.size() == 9);
        } finally {
            for (Book bookForDelete: books) {
                daoSolr.delBook(bookForDelete.getId());
            }
        }
    }
}
