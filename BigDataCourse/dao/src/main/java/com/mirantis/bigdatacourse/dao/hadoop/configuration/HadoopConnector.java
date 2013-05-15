package com.mirantis.bigdatacourse.dao.hadoop.configuration;

import com.mirantis.bigdatacourse.dao.DaoException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;

public class HadoopConnector {

	private Configuration newConf;
	public FileSystem newFS;
	
	@Value("${ip}")
	private String hadoopIP;
	
	@Value("${port}")
	private String hadoopPort;
	
	@Value("${user}")
	private String hadoopUser;
	
	private String hadoopURI;
	
	@Value("${wdirectory}")
	public String workingDirectory;
	
	public int bookID;
	
	
	public HadoopConnector(String hadoopIP, String hadoopPort, String user, String workingDirectory) {
		
		this.hadoopUser = user;
		this.hadoopIP = hadoopIP;
		this.hadoopPort = hadoopPort;
		this.hadoopURI = ("hdfs://" + hadoopIP + ":" + hadoopPort);
		this.workingDirectory = workingDirectory;
		this.bookID = 0;
	}

    public HadoopConnector() {
    }

    private void  setConfiguration() throws DaoException {
		this.newConf = new Configuration();
	}
	
	public FileSystem getFS() throws DaoException {
		if(this.newFS == null) {
			setConfiguration();
			try {
				this.newFS = FileSystem.get(URI.create(hadoopURI),newConf, this.hadoopUser);
			} catch (Exception e) {
				throw new DaoException(e);
			}
			return this.newFS;
		} else {
            return this.newFS;
        }
	}
	
	public void closeConnection() throws DaoException {
		try {
			newFS.close();
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}
	
	public String getIP() {
		return this.hadoopIP;
	}
	
	public String getPort() {
		return this.hadoopPort;
	}
	
	public String getURI() {
		return this.hadoopURI;
	}
}

