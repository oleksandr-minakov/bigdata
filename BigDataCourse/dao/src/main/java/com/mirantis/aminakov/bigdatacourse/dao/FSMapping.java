package com.mirantis.aminakov.bigdatacourse.dao;

import java.io.IOException;
import java.io.InputStream;


public interface FSMapping {
	
	public void setWorkingDirectory(String workingDirectory);
	public String getWorkingDirectory(); 
	
	public String getAbsolutePath(int id) throws DaoException;
	public String getHash(int id) throws DaoException;
	
	public int createDirectoryRecursively(int id)  throws DaoException;
	public int writeFile(int id, InputStream is)  throws DaoException, IOException;
	public int removeFile(int id)  throws DaoException;
	public InputStream readFile(int id)  throws DaoException, IOException;
	
}
