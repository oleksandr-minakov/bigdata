package com.mirantis.aminakov.bigdatacourse.dao;


import java.util.Date;

import org.apache.log4j.Logger;

public class DaoException extends Exception {

	public static final Logger LOG = Logger.getLogger(DaoException.class);
	
	public DaoException(Exception e) {
		LOG.debug("RunTime exception:" + e.getMessage());
	}

	public DaoException (String msg) {
		
		LOG.debug("RunTime exception:" + msg);
		System.err.println(msg);
	}

	private static final long serialVersionUID = 1L;

}

