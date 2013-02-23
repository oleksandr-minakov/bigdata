package com.mirantis.aminakov.bigdatacourse.dao;

public class DeleteException extends DAOException {

	public DeleteException(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	
	public DeleteException (String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}