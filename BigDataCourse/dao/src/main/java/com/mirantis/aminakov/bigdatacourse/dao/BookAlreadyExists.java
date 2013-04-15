package com.mirantis.aminakov.bigdatacourse.dao;

public class BookAlreadyExists extends DaoException {

	public BookAlreadyExists(Exception e) {
		super(e);
	}
	
	public BookAlreadyExists(String msg) {
		super(msg);
	}
	
	private static final long serialVersionUID = 1L;

}
