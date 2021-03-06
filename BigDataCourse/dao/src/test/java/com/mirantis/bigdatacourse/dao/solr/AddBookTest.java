package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.NAS.NASMapping;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class AddBookTest {

    @Test
    public void addBookTest() throws DaoException, IOException, SolrServerException {
        String url = "http://0.0.0.0:8081/solr-web";
        DaoSolr daoSolr = new DaoSolr(url);
        daoSolr.setNASMapping(new NASMapping("/tmp/solr_nas_dev/", 3));
        daoSolr.getServer().deleteByQuery("*:*");
        Book book = new Book();
        book.newBook("title", "author", "genre", new FileInputStream("testbook"));
        daoSolr.addBook(book);
        try {
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("q", "*:*");
            QueryResponse response = daoSolr.getServer().query(params);
            SolrDocumentList results = response.getResults();
            assertTrue(results.size() == 1);
        } finally {
            daoSolr.delBook(book.getId());
        }
    }
}
