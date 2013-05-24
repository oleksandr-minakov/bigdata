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
	
	@Value("#{properties.ip}")
	private String hadoopIP;
	
	@Value("#{properties.port}")
	private String hadoopPort;
	
	@Value("#{properties.user}")
	private String hadoopUser;
	
	private String hadoopURI;
	
	@Value("#{properties.wdirectory}")
	public String workingDirectory;

    @Value("#{properties.worker}")
    public int worker;
	
	public HadoopConnector(String hadoopIP, String hadoopPort, String user, String workingDirectory, int worker) {
		
		this.hadoopUser = user;
		this.hadoopIP = hadoopIP;
		this.hadoopPort = hadoopPort;
		this.hadoopURI = ("hdfs://" + hadoopIP + ":" + hadoopPort);
		this.workingDirectory = workingDirectory;
        this.worker = worker;
	}

    public HadoopConnector() {
    }

    private void  setConfiguration() throws DaoException {
		this.newConf = new Configuration();
        this.hadoopURI = ("hdfs://" + hadoopIP + ":" + hadoopPort);
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

