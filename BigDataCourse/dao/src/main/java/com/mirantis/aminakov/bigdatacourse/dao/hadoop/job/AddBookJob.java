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
		LOG.debug("Getting FileSistem ...");
		String dest = new PathFormer().formAddPath(book, hadoopConf.workingDirectory);
		
		Path path = new Path(dest);
		try {

	        FSDataOutputStream out = fs.create(path);
	        LOG.debug("creating new FS stream writer...");
	        out.write(book.getReadableText().getBytes());
	        LOG.debug("Writing ...");
	        out.close();
	        LOG.debug("Closing writer stream ...");
	        hadoopConf.bookID++;
	        fs.setPermission(path, new FsPermission("777"));
	        LOG.debug("Setting permissions ...");
	        return book.getId();
	        
		} catch (IOException e) {throw new DaoException(e);}
	}
}
	

