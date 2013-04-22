package com.mirantis.aminakov.bigdatacourse.service.restful;

import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.Book;

public interface RESTservice {
	
	public List<Book> findByAuthor(int pageNum, int pageSize, String author);
	public List<Book> findByTitle(int pageNum, int pageSize, String title);
	public List<Book> findByText(int pageNum, int pageSize, String text);
	public List<Book> findByGenre(int pageNum, int pageSize, String genre);
    public int delBook(int id);
	
}
