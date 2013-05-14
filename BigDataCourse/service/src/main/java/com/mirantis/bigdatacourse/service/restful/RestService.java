package com.mirantis.bigdatacourse.service.restful;

import com.mirantis.bigdatacourse.dao.PaginationModel;

public interface RestService {
	
	public PaginationModel findByAuthor(int pageNum, int pageSize, String author);
	public PaginationModel findByTitle(int pageNum, int pageSize, String title);
	public PaginationModel findByText(int pageNum, int pageSize, String text);
	public PaginationModel findByGenre(int pageNum, int pageSize, String genre);
    public int delBook(String id);
	
}
