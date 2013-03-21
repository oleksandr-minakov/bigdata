package com.mirantis.aminakov.bigdatacourse.dao.hadoop;

import java.util.List;
import java.util.TreeSet;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class DaoHDFS implements Dao{

	private HadoopConnector hadoop;
	
	
	@Override
	public int addBook(Book book) throws DaoException {
		// TODO Auto-generated method stub
		return book.getId();
	}

	@Override
	public int delBook(int id) throws DaoException {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeConnection() throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfRecords() {
		// TODO Auto-generated method stub
		return 0;
	}

}
