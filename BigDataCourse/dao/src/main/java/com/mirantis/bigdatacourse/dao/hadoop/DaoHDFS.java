package com.mirantis.bigdatacourse.dao.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.DeleteBookJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetAllBooksJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBookByAuthorJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBookByGenreJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBookByTextJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBookByTitleJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetLastIndexJob;

public class DaoHDFS implements Dao{

    public static final Logger LOG = Logger.getLogger(DaoHDFS.class);
	private HadoopConnector hadoop;
	private int querySize = 0;
	
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
	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		GetAllBooksJob get = new GetAllBooksJob(this.hadoop);
		LOG.debug("Collecting paginated books...");
		books = get.getAllBooksJob(pageNum, pageSize);
		LOG.debug("Updating query size ...");
		this.querySize = get.querySize;
		LOG.info("Returning paginated books");
		return books;
	}

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		GetBookByTitleJob get = new GetBookByTitleJob(this.hadoop);
		LOG.debug("Getting paginated books by title: " + title);
		books = new GetBookByTitleJob(this.hadoop).getBooksBy(pageNum, pageSize, title);
		LOG.debug("Updating query size ...");
		this.querySize = get.querySize;
		LOG.info("Getting paginated books by title: " + title);
		return books;
	}

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		LOG.debug("Getting paginated books by text: " + text);
		books = new GetBookByTextJob(this.hadoop).getBooksBy(pageNum, pageSize, text);
		LOG.debug("Updating query size ...");
		this.querySize = books.size();
		LOG.info("Returning paginated books by text: " + text);
		
		return books;
	}

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		LOG.debug("Getting paginated books by author: " + author);
		books = new GetBookByAuthorJob(this.hadoop).getBooksBy(pageNum, pageSize, author);
		LOG.debug("Updating query size ...");
		this.querySize = books.size();
		LOG.info("Returnin paginated books by author: " + author);
		return books;
	}

	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		LOG.debug("Getting paginated books by genre: " + genre);
		books = new GetBookByGenreJob(this.hadoop).getBooksBy(pageNum, pageSize, genre);
		LOG.debug("Updating query size ...");
		this.querySize = books.size();
		LOG.info("Returnin paginated books by genre: " + genre);
		return books;
	}

	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		LOG.debug("Getting paginated books authors by genre: " + genre);
		books = new GetBookByGenreJob(this.hadoop).getBooksBy(pageNum, pageSize, genre);
		
		TreeSet<String> tree= new TreeSet<String>();
		
		for(Book book: books){
			tree.add(book.getAuthor());
		}
		
		LOG.debug("Updating query size ...");
		this.querySize = books.size();
		LOG.info("Returnin paginated books authors by genre: " + genre);
		return tree;
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
	public int getNumberOfRecords(String whereToSeek, String whatToSeekFor) {
		
		return this.querySize;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		
		if(this.hadoop == null)
			throw new DaoException("Error with hadoop bean inition");
		
	}


	@Override
	public void destroy() throws Exception {
		
		this.hadoop.getFS().close();
		this.hadoop = null;
		
	}

}
