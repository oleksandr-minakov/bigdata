package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.NAS.DaoNAS;
import org.apache.solr.client.solrj.SolrQuery;
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
    public DaoNAS daoNAS = null;

    @Autowired
    Parameters parameters;

    public DaoSolr(Parameters parameters) throws DaoException {
        this.parameters = parameters;
        this.server = new HttpSolrServer(parameters.URL);
        this.daoNAS = parameters.daoNAS;
        parameters.bookId = getMaxId() + 1;
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
            if (daoNAS.writeFile(book.getId(), book.getText()) != book.getId())
                throw new DaoException("I/O error in daoNAS");
            doc.addField("file", daoNAS.getAbsolutePath(book.getId()));
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
            int rm_id = daoNAS.removeFile(id);
            if (rm_id != id)
                throw new DaoException("I/O error in daoNAS " + " rm_id = " + rm_id + " id = " + id);
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
        QueryResponse response;
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
        QueryResponse response;
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
        QueryResponse response;
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
        QueryResponse response;
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

    public int getMaxId() throws DaoException {
        int max = 0;
        QueryResponse response;
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");
            query.addSortField("id", SolrQuery.ORDER.desc);
            response = server.query(query);
            SolrDocumentList results = response.getResults();
            if (results.size() != 0) {
                max = (int) results.get(0).getFieldValue("id");
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return max;
    }
}
