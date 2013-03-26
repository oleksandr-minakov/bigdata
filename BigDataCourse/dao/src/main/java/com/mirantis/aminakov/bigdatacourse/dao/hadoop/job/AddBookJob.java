package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;


public class AddBookJob {
	
	public static final Logger LOG = Logger.getLogger(AddBookJob.class);
	
	private HadoopConnector hadoopConf;
	
	public AddBookJob(HadoopConnector conf) throws DaoException{
		
		this.hadoopConf = conf;
	}
	
	public int addBookJob(Book book) throws DaoException{
		
		book.setId(hadoopConf.bookID);
		FileSystem fs = hadoopConf.getFS();
		String dest = new PathFormer().formAddPath(book, hadoopConf.workingDirectory);
		
		Path path = new Path(dest);
		try {

	        FSDataOutputStream out = fs.create(path);
	        
	        out.write(book.getReadbleText().getBytes());	        
	        out.close();
	        
	        hadoopConf.bookID++;
	        fs.setPermission(path, new FsPermission("777"));
	        return book.getId();
	        
		} catch (IOException e) {throw new DaoException(e);}
	}
}
	

