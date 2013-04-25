package com.mirantis.bigdatacourse.service;

import java.util.List;
import java.util.TreeSet;

import com.mirantis.bigdatacourse.dao.Book;

public interface Service {
	
	public int addBook(Book book);
    public int delBook(String id);
	public List<Book> getAllBooks(int pageNum, int pageSize);
	public List<Book> findByAuthor(int pageNum, int pageSize, String author);
	public List<Book> findByTitle(int pageNum, int pageSize, String title);
	public List<Book> findByText(int pageNum, int pageSize, String text);
	public List<Book> findByGenre(int pageNum, int pageSize, String genre);
	public TreeSet<String> findAuthorByGenre(int pageNum, int pageSize, String genre);
	
	public int getNumberOfRecordsByAuthor(String whatToSeekFor);
	public int getNumberOfRecordsByTitle(String whatToSeekFor);
	public int getNumberOfRecordsByGenre(String whatToSeekFor);
	public int getNumberOfRecordsByText(String whatToSeekFor);
	
}
