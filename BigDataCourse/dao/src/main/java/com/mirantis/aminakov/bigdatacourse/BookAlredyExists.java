package com.mirantis.aminakov.bigdatacourse;

public class BookAlredyExists extends DaoException {

	public BookAlredyExists(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	
	public BookAlredyExists (String msg) {
		super(msg);
	}
	
	private static final long serialVersionUID = 1L;

}
