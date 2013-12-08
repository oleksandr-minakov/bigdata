package com.mirantis.bigdatacourse.dao;

import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.io.InputStream;


public interface FSMapping extends DisposableBean{
	
	public void setWorkingDirectory(String workingDirectory);
	public String getWorkingDirectory(); 
	
	public String getAbsolutePath(String id) throws DaoException;
	public String getHash(String id) throws DaoException;
	
	public int createDirectoryRecursively(String id)  throws DaoException;
	public int writeFile(String id, InputStream is)  throws DaoException, IOException;
	public int removeFile(String id)  throws DaoException;
	public InputStream readFile(String id)  throws DaoException, IOException;
	
}
