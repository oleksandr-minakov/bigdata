package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.*;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DaoSolr implements Dao {

    @Value("#{properties.worker}")
    private int worker;

    public static final Logger LOG = Logger.getLogger(DaoSolr.class);
    private SolrServer server = null;

    @Autowired
    private FSMapping NASMapping;

    public SolrServer getServer() {
        return server;
    }

    public void setNASMapping(FSMapping NASMapping) {
        this.NASMapping = NASMapping;
    }

    public DaoSolr() {
    }

    @Autowired
    public DaoSolr(@Value("#{properties.solr_url}") String url) throws DaoException {
        if (server == null) {
            server = new HttpSolrServer(url);
        }
    }

    @Override
    public int addBook(Book book) throws DaoException {

        List<String> mods = new ArrayList<>();
        KeyGenerator idGen = new KeyGenerator();

        mods.add(String.valueOf(Thread.activeCount()));
        mods.add(Thread.currentThread().getName());
        mods.add(Thread.currentThread().toString());
        mods.add(Thread.currentThread().getState().toString());
        mods.add(Integer.toString(worker));
        mods.add(String.valueOf(new Date().getTime()));

        String newID = idGen.getNewID(mods);
        book.setId(newID);
        try {
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
            if ( NASMapping.writeFile(book.getId(), book.getText()) != 0) {
                LOG.debug("I/O error in NASMapping");
                throw new DaoException("I/O error in NASMapping");
            }
            LOG.debug("Write book in NAS. Id = " + book.getId());
            doc.addField("file", NASMapping.getAbsolutePath(book.getId()));
            doc.addField("text", book.getReadableText());
            server.add(doc);
            server.commit();
        } catch (Exception e) {
            try {
                server.rollback();
            } catch (SolrServerException | IOException e1) {
                throw new DaoException("Add exception " + e.getMessage());
            }
        }
        LOG.info("Add book. Id = " + book.getId());
        LOG.debug("Add book. Id = " + book.getId());
        return 0;
    }

    @Override
    public int delBook(String id) throws DaoException {
        try {
            server.deleteByQuery("id:" + id);
            int rm_id = NASMapping.removeFile(id);
            if (rm_id != 0) {
                server.rollback();
                throw new DaoException("I/O error in NASMapping " + " rm_id = " + rm_id + " id = " + id);
            }
            server.commit();
            LOG.debug("Delete book from NAS. Id = " + id);
        } catch (SolrServerException | IOException e) {
            throw new DaoException("Delete exception " + e.getMessage());
        }
        LOG.info("Delete book. Id = " + id);
        LOG.debug("Delete book. Id = " + id);
        return 0;
    }

    @Override
    public PaginationModel getAllBooks(int pageNum, int pageSize) throws DaoException {
        List<Book> books = new ArrayList<>();
        PaginationModel model = new PaginationModel();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "*:*");
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            model.setNumberOfRecords((int) results.getNumFound());
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, NASMapping));
                } catch (IOException e) {
                    LOG.debug("I/O error in NASMapping");
                    throw new DaoException("I/O error in NASMapping " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get all books");
        LOG.info("Get all books");
        model.setBooks(books);
        return model;
    }

    @Override
    public PaginationModel getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        List<Book> books = new ArrayList<>();
        PaginationModel model = new PaginationModel();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "title:" + "*" + title + "*");
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            model.setNumberOfRecords((int) results.getNumFound());
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, NASMapping));
                } catch (IOException e) {
                    LOG.debug("I/O error in NASMapping");
                    throw new DaoException("I/O error in NASMapping " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by title -> " + title);
        LOG.info("Get book by title -> " + title);
        model.setBooks(books);
        return model;
    }

    @Override
    public PaginationModel getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        List<Book> books = new ArrayList<>();
        PaginationModel model = new PaginationModel();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "author:" + "*" + author + "*");
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            model.setNumberOfRecords((int) results.getNumFound());
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, NASMapping));
                } catch (IOException e) {
                    LOG.debug("I/O error in NASMapping");
                    throw new DaoException("I/O error in NASMapping " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by author -> " + author);
        LOG.info("Get book by author -> " + author);
        model.setBooks(books);
        return model;
    }

    @Override
    public PaginationModel getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        List<Book> books = new ArrayList<>();
        PaginationModel model = new PaginationModel();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "genre:" + "*" + genre + "*");
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            model.setNumberOfRecords((int) results.getNumFound());
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, NASMapping));
                } catch (IOException e) {
                    LOG.debug("I/O error in NASMapping");
                    throw new DaoException("I/O error in NASMapping " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by genre -> " + genre);
        LOG.info("Get book by genre -> " + genre);
        model.setBooks(books);
        return model;
    }

    //TODO add search index
    @Override
    public PaginationModel getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        List<Book> books = new ArrayList<>();
        PaginationModel model = new PaginationModel();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", "text:" + "*" + text + "*");
        params.set("rows", pageSize);
        params.set("start", (pageNum - 1) * pageSize);
        QueryResponse response;
        try {
            response = server.query(params);
            SolrDocumentList results = response.getResults();
            model.setNumberOfRecords((int) results.getNumFound());
            for (SolrDocument result : results) {
                try {
                    books.add(BookUtils.map(result, NASMapping));
                } catch (IOException e) {
                    LOG.debug("I/O error in NASMapping");
                    throw new DaoException("I/O error in NASMapping " + e.getMessage());
                }
            }
        } catch (SolrServerException e) {
            throw new DaoException(e);
        }
        LOG.debug("Get book by text -> " + text);
        LOG.info("Get book by text -> " + text);
        model.setBooks(books);
        return model;
    }

    @Override
    public void closeConnection() throws DaoException {
        NASMapping = null;
        server = null;
        LOG.debug("Close connection");
    }

    @Override
    public void destroy() throws Exception {
        closeConnection();
        LOG.debug("IN SECTION destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.debug("IN SECTION afterPropertiesSet");
        if(this.server == null) {
            throw new DaoException("Error with Solr bean initialization");
        }
    }
}
