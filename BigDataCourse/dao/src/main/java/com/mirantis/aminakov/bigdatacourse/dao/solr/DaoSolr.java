package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DaoSolr implements Dao {

    public SolrServer server = null;

    @Autowired
    Parameters parameters;

    public DaoSolr(Parameters parameters) {
        this.parameters = parameters;
        server = new HttpSolrServer(parameters.URL);
    }

    @Override
    public int addBook(Book book) throws DaoException {
        book.setId(parameters.bookId);
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", book.getId());
            doc.addField("title", book.getTitle());
            doc.addField("author", book.getAuthor());
            doc.addField("genre", book.getGenre());
            doc.addField("file", book.getText()); //TODO add link to file
            doc.addField("text", book.getReadableText());
            server.add(doc);
            server.commit();
        } catch (Exception e) {
            throw new DaoException("Add exception " + e.getMessage());
        }
        parameters.bookId++;
        return book.getId();
    }

    @Override
    public int delBook(int id) throws DaoException {
        try {
            server.deleteByQuery("id:" + id);
            server.commit();
        } catch (SolrServerException | IOException e) {
            throw new DaoException("Delete exception " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Book> getAllBooks(int pageNum, int pageSize) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "*:*");
        QueryResponse response = null;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
            books = BookUtils.pagination(pageNum, pageSize, books);
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return books;
    }

    @Override
    public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "title:" + title);
        QueryResponse response = null;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
            books = BookUtils.pagination(pageNum, pageSize, books);
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return books;
    }

    @Override
    public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "author:" + author);
        QueryResponse response = null;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
            books = BookUtils.pagination(pageNum, pageSize, books);
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return books;
    }

    @Override
    public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "genre:" + genre);
        QueryResponse response = null;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
            books = BookUtils.pagination(pageNum, pageSize, books);
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return books;
    }

    @Override
    public List<Book> getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void closeConnection() throws DaoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getNumberOfRecords() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getMaxId() {
        int max = 0;
        /*List<String> keys = getAllRowKeys();
        if(keys.size() != 0 ){

            List<String> bookIDs = new ArrayList<String>();

            int[] ids = new int[keys.size()];
            String[] stringIDs = new String[keys.size()];

            for(String key:keys){
                bookIDs.add(key.substring("book ".length()));
            }

            bookIDs.toArray(stringIDs);

            for(int i = 0; i < bookIDs.size(); ++i){

                ids[i] = Integer.valueOf(stringIDs[i]);
            }
            Arrays.sort(ids);
            max = ids[ids.length-1];
        }*/
        return max;
    }
}
