package com.mirantis.aminakov.bigdatacourse.service;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoApp;

public class ServiceCassandra implements Service{

	public static final Logger LOG = Logger.getLogger(ServiceToDao.class);
	private DaoApp dao;
	
	public ServiceCassandra(DaoApp dao){
		
		this.dao = dao;
	}

	@Override
	public int addBook(Book book) {
		
		try {
			dao.addBook(book);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());}
		return book.getId();
	}

    @Override
    public int delBook(int id) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List<Book> getAllBooks(int pageNum, int pageSize) {
		
		try {
			return dao.getAllBooks(pageNum, pageSize);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());}
		return null;
	}

	@Override
	public List<Book> findByAuthor(int pageNum, int pageSize, String author) {
		
		try {
			return dao.getBookByAuthor(pageNum, pageSize, author);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());}

		return null;
	}

	@Override
	public List<Book> findByTitle(int pageNum, int pageSize, String title) {
		
		try {
			return dao.getBookByTitle(pageNum, pageSize, title);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());}

		return null;
	}

	@Override
	public List<Book> findByText(int pageNum, int pageSize, String text) {
		
		try {
			return dao.getBookByText(pageNum, pageSize, text);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());}

		return null;
		
	}

	@Override
	public List<Book> findByGenre(int pageNum, int pageSize, String genre) {
		
		try {
			return dao.getBookByGenre(pageNum, pageSize, genre);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());}

		return null;
		
	}

	@Override
	public TreeSet<String> findAuthorByGenre(int pageNum, int pageSize,
			String genre) {
		
		try {
			return dao.getAuthorByGenre(pageNum, pageSize, genre);
		} catch (Exception e) {
			LOG.debug("[" + new Date()+"]"+ "RunTime exception:" + e.getMessage());}

		return null;
	}

	@Override
	public int getNumberOfRecords() {

		return dao.getNumberOfRecords();
	}

	
	
}
