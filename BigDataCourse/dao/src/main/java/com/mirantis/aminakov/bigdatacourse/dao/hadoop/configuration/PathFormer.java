package com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration;

import com.mirantis.aminakov.bigdatacourse.dao.Book;

public class PathFormer {
	/*
	 * root suppose be ended with /. Such as "home/user/"
	 */

	public String formAddPath(Book book, String root){
		return new String(root + book.getId() + "/" + book.getAuthor() + "/" + book.getGenre() + "/" + book.getTitle());
	}
	
	public String formDeletePath(String root, int id){
		
		return new String(root + String.valueOf(id) + "/");
	}

}
