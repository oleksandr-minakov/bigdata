package com.mirantis.bigdatacourse.dao;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.DisposableBean;


public interface FSMapping extends DisposableBean{
	
	public void setWorkingDirectory(String workingDirectory);
	public String getWorkingDirectory(); 
	
	public String getAbsolutePath(int id) throws DaoException;
	public String getHash(int id) throws DaoException;
	
	public int createDirectoryRecursively(int id)  throws DaoException;
	public int writeFile(int id, InputStream is)  throws DaoException, IOException;
	public int removeFile(int id)  throws DaoException;
	public InputStream readFile(int id)  throws DaoException, IOException;
	
}
