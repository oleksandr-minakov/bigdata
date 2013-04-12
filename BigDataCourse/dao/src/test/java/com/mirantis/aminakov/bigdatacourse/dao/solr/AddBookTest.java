package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.NAS.DaoNAS;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class AddBookTest {

    @Test
    public void addBookTest() throws DaoException, IOException, SolrServerException {
        DaoNAS daoNAS = new DaoNAS("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters("http://localhost:8080/solr-web", daoNAS);
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.server.deleteByQuery("*:*");
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        int id = daoSolr.addBook(book);
        try {
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("q", "*:*");
            QueryResponse response = daoSolr.server.query(params);
            SolrDocumentList results = response.getResults();
            assertTrue(results.size() == 1);
        } finally {
            daoSolr.delBook(id);
        }
    }
}
