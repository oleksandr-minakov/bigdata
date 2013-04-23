package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.NAS.NASMapping;
import com.mirantis.bigdatacourse.dao.solr.DaoSolr;
import com.mirantis.bigdatacourse.dao.solr.Parameters;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GetMaxIdTest {

    @Test
    public void getMaxIdTest() throws DaoException, IOException, SolrServerException {
        NASMapping daoNAS = new NASMapping("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters();
        parameters.URL = "http://0.0.0.0:8080/solr-web";
        parameters.daoNAS = daoNAS;
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.getServer().deleteByQuery("*:*");
        Book book = new Book();
        for (int i = 1; i < 100; i++) {
            book.newBook("title" + i, "author" + i, "genre" + i, new FileInputStream("testbook"));
            daoSolr.addBook(book);
        }
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");
            query.addSortField("id", SolrQuery.ORDER.desc);
            QueryResponse response = daoSolr.getServer().query(query);
            SolrDocumentList results = response.getResults();
            assertTrue(results.get(0).getFieldValue("id").equals(99));
        } finally {
            for (int i = 1; i < 100; i++) {
                daoSolr.delBook(i);
            }
        }
    }
}
