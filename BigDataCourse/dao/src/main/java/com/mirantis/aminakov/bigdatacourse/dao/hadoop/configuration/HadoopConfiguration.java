package com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration;

import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class HadoopConfiguration {

	private Configuration newConf;
	public FileSystem newFS;
	private List<String> xmlConfs;
	
	public HadoopConfiguration(List<String> configs){
		
		setXMLs(configs);
	}
	
	
	private void setXMLs(List<String> list){
		
		this.xmlConfs = list;
	}
	
	private void  setConfiguration(){
		
		this.newConf = new Configuration();
		for(String xml: xmlConfs)
			this.newConf.addResource(xml);
		
	}
	
	public FileSystem getFS() throws IOException{
		
		if(this.newFS == null){
			setConfiguration();
			this.newFS = FileSystem.get(newConf);
			return this.newFS;
		}
		else
			return this.newFS;
	}
}

