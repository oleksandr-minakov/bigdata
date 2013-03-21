package com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration;

import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;

public class HadoopConnector {

	private Configuration newConf;
	public FileSystem newFS;
	private String hadoopIP;
	private String hadoopPort;
	private String hadoopUser;
	private String hadoopURI;
	public String workingDirectory;
	public int bookID;
	
	
	public HadoopConnector(String hadoopIP, String hadoopPort, String user, String workingDirectory){
		this.hadoopUser = user;
		this.hadoopIP = hadoopIP;
		this.hadoopPort = hadoopPort;
		hadoopURI = ("hdfs://" + hadoopIP + ":" + hadoopPort);
		this.workingDirectory = workingDirectory;
		bookID = 0;
	}
	
	private void  setConfiguration() throws DaoException{
		
		this.newConf = new Configuration();
	}
	
	public FileSystem getFS() throws DaoException{
		
		if(this.newFS == null){
			
			setConfiguration();
			try {
				this.newFS = FileSystem.get(URI.create(hadoopURI),newConf, this.hadoopUser);
			} catch (Exception e) {throw new DaoException(e);}
			
			return this.newFS;
		}
		else
			return this.newFS;
	}
	
	public void closeConnection() throws DaoException{
		try {newFS.close();} catch (IOException e) {throw new DaoException(e);}
	}
	
	public String getIP(){
		return this.hadoopIP;
	}
	
	public String getPort(){
		return this.hadoopPort;
	}
	
	public String getURI(){
		return this.hadoopURI;
	}
}

