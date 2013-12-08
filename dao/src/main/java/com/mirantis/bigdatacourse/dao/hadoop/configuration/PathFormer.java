package com.mirantis.bigdatacourse.dao.hadoop.configuration;

import com.mirantis.bigdatacourse.dao.Book;

public class PathFormer {
	
	/*
	 * root suppose be ended with /. Such as "/bookshelf/books/"
	 */

	public String formAddPath(Book book, String root) {
		return root + "/" + book.getId() + "/" + book.getAuthor() + "/" + book.getGenre() + "/" + book.getTitle();
	}
	
	public String formDeletePath(String root, String id) {
		return root + id + "/";
	}
}
