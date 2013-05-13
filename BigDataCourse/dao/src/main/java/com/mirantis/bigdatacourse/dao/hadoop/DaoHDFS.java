package com.mirantis.bigdatacourse.dao.hadoop;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.PaginationModel;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.*;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DaoHDFS implements Dao {

    public static final Logger LOG = Logger.getLogger(DaoHDFS.class);
	private HadoopConnector hadoop;
	
	public DaoHDFS(HadoopConnector hadoop) throws DaoException {
		this.hadoop = hadoop;
		LOG.debug("Getting connection from Spring context...");
		this.hadoop.bookID = new GetLastIndexJob(this.hadoop).getIncrementedNewID();
		LOG.debug("Calculating new id...");
		LOG.info("Hello, Hadoop. Connection established");
	}
	
	@Override
	public int addBook(Book book) throws DaoException {
		int res  = new AddBookJob(this.hadoop).addBookJob(book);
		LOG.info("Adding book with id: " + book.getId());
		return res;
	}

	@Override
	public int delBook(String id) throws DaoException {
		int res = new DeleteBookJob(this.hadoop).deleteBookJob(id);
		LOG.info("Deleting book with id: " + id);
		return res;
	}

	@Override
	public PaginationModel getAllBooks(int pageNum, int pageSize) throws DaoException {
        PaginationModel model = new PaginationModel();
		GetAllBooksJob get = new GetAllBooksJob(this.hadoop);
		LOG.debug("Collecting paginated books...");
        model.setBooks(get.getAllBooksJob(pageNum, pageSize));
        LOG.debug("Updating query size ...");
		model.setNumberOfRecords(get.querySize);                     //TODO add getNumberOfRecords for query
		LOG.info("Returning paginated books");
		return model;
	}

	@Override
	public PaginationModel getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        PaginationModel model = new PaginationModel();
        GetBookByTitleJob get = new GetBookByTitleJob(this.hadoop);
		LOG.debug("Getting paginated books by title: " + title);
        model.setBooks(new GetBookByTitleJob(this.hadoop).getBooksBy(pageNum, pageSize, title));
        LOG.debug("Updating query size ...");
        model.setNumberOfRecords(get.querySize);                     //TODO add getNumberOfRecords for query
        LOG.info("Getting paginated books by title: " + title);
		return model;
	}

	@Override
	public PaginationModel getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        PaginationModel model = new PaginationModel();
        GetBookByTextJob get = new GetBookByTextJob(this.hadoop);
        LOG.debug("Getting paginated books by text: " + text);
        model.setBooks(new GetBookByTextJob(this.hadoop).getBooksBy(pageNum, pageSize, text));
        LOG.debug("Updating query size ...");
		model.setNumberOfRecords(0);                                 //TODO add getNumberOfRecords for query
		LOG.info("Returning paginated books by text: " + text);
		return model;
	}

	@Override
	public PaginationModel getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        PaginationModel model = new PaginationModel();
		LOG.debug("Getting paginated books by author: " + author);
        model.setBooks(new GetBookByAuthorJob(this.hadoop).getBooksBy(pageNum, pageSize, author));
        LOG.debug("Updating query size ...");
		model.setNumberOfRecords(0);                                 //TODO add getNumberOfRecords for query
		LOG.info("Returning paginated books by author: " + author);
		return model;
	}

	@Override
	public PaginationModel getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        PaginationModel model = new PaginationModel();
		LOG.debug("Getting paginated books by genre: " + genre);
        model.setBooks(new GetBookByGenreJob(this.hadoop).getBooksBy(pageNum, pageSize, genre));
        LOG.debug("Updating query size ...");
		model.setNumberOfRecords(0);                                 //TODO add getNumberOfRecords for query
		LOG.info("Returning paginated books by genre: " + genre);
		return model;
	}

	@Override
	public void closeConnection() throws DaoException {
		try {
			LOG.info("Closing connection fo HDFS");
			this.hadoop.getFS().close();
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(this.hadoop == null)
			throw new DaoException("Error with hadoop bean initialization.");
	}

	@Override
	public void destroy() throws Exception {
		this.hadoop.getFS().close();
		this.hadoop = null;
	}
}
