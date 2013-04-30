package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.NAS.NASMapping;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GetNumberOfRecordsTest {

    @Test
    public void getNumberOfRecordsTest() throws DaoException, IOException, SolrServerException {
        List<Book> books = new ArrayList<Book>();
        NASMapping daoNAS = new NASMapping("/tmp/solr_nas/", 3);
        Parameters parameters = new Parameters();
        parameters.URL = "http://0.0.0.0:8080/solr-web";
        parameters.daoNAS = daoNAS;
        DaoSolr daoSolr = new DaoSolr(parameters);
        daoSolr.getServer().deleteByQuery("*:*");
        Book book = new Book();
        for (int i = 1; i < 55; i++) {
            book.newBook("title" + i, "author" + i, "genre" + i, new FileInputStream("testbook"));
            daoSolr.addBook(book);
            books.add(book);
        }
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");
            query.addSortField("id", SolrQuery.ORDER.desc);
            QueryResponse response = daoSolr.getServer().query(query);
            SolrDocumentList results = response.getResults();
            daoSolr.getAllBooks(1,1);
            assertTrue(daoSolr.getNumberOfRecords("", "") == 54);
        } finally {
            for (Book bookForDelete: books) {
                daoSolr.delBook(bookForDelete.getId());
            }
        }
    }
}
