package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.BookAlreadyExists;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.NAS.NASMapping;
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
    public NASMapping daoNAS = null;
    private int numberOfRecords = -1;

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
            List<Book> books = new ArrayList<Book>();
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("q", "title:" + book.getTitle());
            params.set("rows", 1);
            params.set("start", 0);
            QueryResponse response;
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            if (results.size() != 0)
                throw new BookAlreadyExists("Book already exists.");
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
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
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
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
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
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
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
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return books;
    }

    //TODO add search index
    @Override
    public List<Book> getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        List<Book> books = new ArrayList<Book>();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "text:*" + text + "*");
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                books.add(BookUtils.map(result));
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return books;
    }

    @Override
    public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        TreeSet<String> authors = new TreeSet<>();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "*:*");
        QueryResponse response;
        int allRecords;
        try {
            response = server.query(params);
            allRecords = (int) response.getResults().getNumFound();
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        params.set("q", "genre:" + genre);
        params.set("rows", allRecords);
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                authors.add((String) result.get("author"));
            }
            authors = BookUtils.pagination(pageNum, pageSize, authors);
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        return authors;
    }

    @Override
    public void closeConnection() throws DaoException {
        try {
            server.rollback();
        } catch (SolrServerException | IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int getNumberOfRecords() {
        return numberOfRecords;
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

    @Override
    public void destroy() throws Exception {
        try {
            server.rollback();
        } catch (SolrServerException | IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if((this.parameters == null) || (this.daoNAS == null) || (this.server == null)) {
            throw new DaoException("Error with memcached bean initialization");
        }
    }
}
