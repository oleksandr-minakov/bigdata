package com.mirantis.bigdatacourse.dao;

public class DeleteException extends DaoException {

	public DeleteException(Exception e) {
		super(e);
	}
	
	public DeleteException (String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}