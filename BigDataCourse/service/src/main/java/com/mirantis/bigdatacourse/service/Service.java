package com.mirantis.bigdatacourse.service;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.PaginationModel;

public interface Service {
	
	public int addBook(Book book);
    public int delBook(String id);
	public PaginationModel getAllBooks(int pageNum, int pageSize);
	public PaginationModel findByAuthor(int pageNum, int pageSize, String author);
	public PaginationModel findByTitle(int pageNum, int pageSize, String title);
	public PaginationModel findByText(int pageNum, int pageSize, String text);
	public PaginationModel findByGenre(int pageNum, int pageSize, String genre);
}
