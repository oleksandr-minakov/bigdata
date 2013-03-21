package com.mirantis.aminakov.bigdatacourse.dao.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.DeleteBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetAllBooksJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByAuthorJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByGenreJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByTextJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBookByTitleJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetLastIndexJob;

public class DaoHDFS implements Dao{

	private HadoopConnector hadoop;
	private int querySize = 0;
	
	public DaoHDFS(HadoopConnector hadoop) throws DaoException{
		
		this.hadoop = hadoop;
		this.hadoop.bookID = new GetLastIndexJob(this.hadoop).getIncrementedNewID();
	}
	
	
	@Override
	public int addBook(Book book) throws DaoException {
		
		int res  = new AddBookJob(this.hadoop).addBookJob(book);
		return res;
	}

	@Override
	public int delBook(int id) throws DaoException {
		
		int res = new DeleteBookJob(this.hadoop).deleteBookJob(id);
		return res;
	}

	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		GetAllBooksJob get = new GetAllBooksJob(this.hadoop);
		books = get.getAllBooksJob(pageNum, pageSize);
		
		this.querySize = get.querySize;
		return books;
	}

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		GetBookByTitleJob get = new GetBookByTitleJob(this.hadoop);
		books = new GetBookByTitleJob(this.hadoop).getBooksBy(pageNum, pageSize, title);
		
		this.querySize = get.querySize;
		
		return books;
	}

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		
		books = new GetBookByTextJob(this.hadoop).getBooksBy(pageNum, pageSize, text);
		
		this.querySize = books.size();
		
		return books;
	}

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		
		books = new GetBookByAuthorJob(this.hadoop).getBooksBy(pageNum, pageSize, author);
		
		this.querySize = books.size();
		return books;
	}

	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre)
			throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		books = new GetBookByGenreJob(this.hadoop).getBooksBy(pageNum, pageSize, genre);
		return books;
	}

	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException {
		
		List<Book> books = new ArrayList<Book>();
		
		books = new GetBookByGenreJob(this.hadoop).getBooksBy(pageNum, pageSize, genre);
		
		TreeSet<String> tree= new TreeSet<String>();
		
		for(Book book: books){
			tree.add(book.getAuthor());
		}
		
		return tree;
	}

	@Override
	public void closeConnection() throws DaoException {
		
		try {
			
			this.hadoop.getFS().close();
			
		} catch (IOException e) {
			
			throw new DaoException(e);
		}
		
	}

	@Override
	public int getNumberOfRecords() {
		
		return this.querySize;
	}

}
