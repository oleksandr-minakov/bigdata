package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.NAS.NASMapping;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;

public class GetAuthorByGenreTest {

    @Test
    public void getAuthorByGenreTest() throws IOException, DaoException, SolrServerException {
        TreeSet<String> authors;
        NASMapping daoNAS = new NASMapping("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters("http://localhost:8080/solr-web", daoNAS);
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.server.deleteByQuery("*:*");
        Book book = new Book();
        for (int i = 1; i < 10; i++) {
            book.newBook("title" + i, "author" + i, "genre", new FileInputStream("testbook"));
            daoSolr.addBook(book);
        }
        try {
            authors = daoSolr.getAuthorByGenre(1, 10, "genre");
            assertTrue(authors.size() == 9);
        } finally {
            for (int i = 1; i < 10; i++) {
                daoSolr.delBook(i);
            }
        }
    }
}
