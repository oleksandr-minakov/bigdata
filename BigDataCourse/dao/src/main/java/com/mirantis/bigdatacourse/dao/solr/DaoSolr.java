package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.BookAlreadyExists;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.NAS.NASMapping;

import org.apache.log4j.Logger;
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

    private SolrServer server = null;
    private NASMapping daoNAS = null;
    private int numberOfRecords = -1;
    public static final Logger LOG = Logger.getLogger(DaoSolr.class);

    @Autowired
    Parameters parameters;

    public DaoSolr(Parameters parameters) throws DaoException {
        LOG.debug("In constructor");
        this.parameters = parameters;
    }

    public SolrServer getServer() throws DaoException {
        if (server == null) {
            server = new HttpSolrServer(parameters.URL);
            LOG.debug("Set server");
            this.daoNAS = parameters.daoNAS;
            LOG.debug("Set daoNAS");
            parameters.bookId = getMaxId() + 1;
            LOG.debug("Set bookID");
        }
        LOG.info("Get server");
        return server;
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
            response = getServer().query(params);
            SolrDocumentList results = response.getResults();
            if (results.size() != 0)
                throw new BookAlreadyExists("Book already exists.");
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", book.getId());
            doc.addField("title", book.getTitle());
            doc.addField("author", book.getAuthor());
            doc.addField("genre", book.getGenre());
            if (daoNAS.writeFile(book.getId(), book.getText()) != book.getId()) {
                LOG.debug("I/O error in daoNAS");
                throw new DaoException("I/O error in daoNAS");
            }
            LOG.debug("Write book in NAS. Id = " + book.getId());
            doc.addField("file", daoNAS.getAbsolutePath(book.getId()));
            doc.addField("text", book.getReadableText());
            getServer().add(doc);
            getServer().commit();
        } catch (Exception e) {
            try {
                getServer().rollback();
            } catch (SolrServerException | IOException e1) {
                throw new DaoException("Add exception " + e.getMessage());
            }
        }
        parameters.bookId++;
        LOG.info("Add book. Id = " + book.getId());
        LOG.debug("Add book. Id = " + book.getId());
        return book.getId();
    }

    @Override
    public int delBook(int id) throws DaoException {
        try {
            getServer().deleteByQuery("id:" + id);
            int rm_id = daoNAS.removeFile(id);
            if (rm_id != id) {
                getServer().rollback();
                throw new DaoException("I/O error in daoNAS " + " rm_id = " + rm_id + " id = " + id);
            }
            getServer().commit();
            LOG.debug("Delete book from NAS. Id = " + id);
        } catch (SolrServerException | IOException e) {
            throw new DaoException("Delete exception " + e.getMessage());
        }
        LOG.info("Delete book. Id = " + id);
        LOG.debug("Delete book. Id = " + id);
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
            response = getServer().query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, daoNAS));
                } catch (IOException e) {
                    LOG.debug("I/O error in daoNAS");
                    throw new DaoException("I/O error in daoNAS " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get all books");
        LOG.info("Get all books");
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
            response = getServer().query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, daoNAS));
                } catch (IOException e) {
                    LOG.debug("I/O error in daoNAS");
                    throw new DaoException("I/O error in daoNAS " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by title -> " + title);
        LOG.info("Get book by title -> " + title);
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
            response = getServer().query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, daoNAS));
                } catch (IOException e) {
                    LOG.debug("I/O error in daoNAS");
                    throw new DaoException("I/O error in daoNAS " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by author -> " + author);
        LOG.info("Get book by author -> " + author);
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
            response = getServer().query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, daoNAS));
                } catch (IOException e) {
                    LOG.debug("I/O error in daoNAS");
                    throw new DaoException("I/O error in daoNAS " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by genre -> " + genre);
        LOG.info("Get book by genre -> " + genre);
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
            response = getServer().query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, daoNAS));
                } catch (IOException e) {
                    LOG.debug("I/O error in daoNAS");
                    throw new DaoException("I/O error in daoNAS " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by text -> " + text);
        LOG.info("Get book by text -> " + text);
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
            response = getServer().query(params);
            allRecords = (int) response.getResults().getNumFound();
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        params.set("q", "genre:" + genre);
        params.set("rows", allRecords);
        try {
            response = getServer().query(params);
            SolrDocumentList results = response.getResults();
            numberOfRecords = (int) results.getNumFound();
            for (SolrDocument result : results) {
                authors.add((String) result.get("author"));
            }
            authors = BookUtils.pagination(pageNum, pageSize, authors);
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get author by genre -> " + genre);
        LOG.info("Get author by genre -> " + genre);
        return authors;
    }

    @Override
    public void closeConnection() throws DaoException {
        daoNAS = null;
        server = null;
        LOG.debug("Close connection");
    }

    @Override
    public int getNumberOfRecords() {
        LOG.debug("Get number of records " + numberOfRecords);
        LOG.info("Get number of records " + numberOfRecords);
        return numberOfRecords;
    }

    public int getMaxId() throws DaoException {
        int max = 0;
        QueryResponse response;
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");
            query.addSortField("id", SolrQuery.ORDER.desc);
            response = getServer().query(query);
            SolrDocumentList results = response.getResults();
            if (results.size() != 0) {
                max = (int) results.get(0).getFieldValue("id");
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get max id = " + max);
        LOG.info("Get max id = " + max);
        return max;
    }

    @Override
    public void destroy() throws Exception {
        closeConnection();
        LOG.debug("IN SECTION destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.debug("IN SECTION afterPropertiesSet");
        if(this.parameters == null) {
            throw new DaoException("Error with Solr bean initialization");
        }
    }
}
