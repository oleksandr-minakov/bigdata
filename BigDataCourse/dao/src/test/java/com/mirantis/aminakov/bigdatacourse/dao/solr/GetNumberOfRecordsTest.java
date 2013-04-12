package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.NAS.DaoNAS;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GetNumberOfRecordsTest {

    @Test
    public void getNumberOfRecordsTest() throws DaoException, IOException, SolrServerException {
        DaoNAS daoNAS = new DaoNAS("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters("http://localhost:8080/solr-web", daoNAS);
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.server.deleteByQuery("*:*");
        Book book = new Book();
        for (int i = 1; i < 55; i++) {
            book.newBook("title" + i, "author" + i, "genre" + i, new FileInputStream("testbook"));
            daoSolr.addBook(book);
        }
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");
            query.addSortField("id", SolrQuery.ORDER.desc);
            QueryResponse response = daoSolr.server.query(query);
            SolrDocumentList results = response.getResults();
            assertTrue(results.getNumFound() == 54);
        } finally {
            for (int i = 1; i < 55; i++) {
                daoSolr.delBook(i);
            }
        }
    }
}
