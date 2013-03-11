package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConfiguration;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;


public class AddBookJob {
	
	public static final Logger LOG = Logger.getLogger(AddBookJob.class);
	
	private FileSystem hadoopFs;
	
	public AddBookJob(HadoopConfiguration conf) throws DaoException{
		
		try {
			this.hadoopFs = conf.getFS();
		} catch (IOException e) {throw new DaoException(e);}
	}
	
	public int addBookJob(Book book) throws DaoException{
		
		String dest = new PathFormer().formAddPath(book, hadoopFs.getHomeDirectory().toString());
		
		Path path = new Path(dest);
		try {
			
			hadoopFs.mkdirs(path, FsPermission.valueOf("rwxrwxrwx"));
			
			
	        if (hadoopFs.exists(path)) {
	            LOG.debug("File " + dest + " already exists");
	            return -1;
	        }
			
	        FSDataOutputStream out = hadoopFs.create(path);
	        InputStream in = new BufferedInputStream( new FileInputStream( new File( book.getTitle() ) ) );
	        byte[] toWrap = new byte[in.available()]; in.read(toWrap);
	        
	        out.write(toWrap);
	        
	        out.close();
	        in.close();
	        hadoopFs.close();
		} catch (IOException e) {throw new DaoException(e);}

        
		return book.getId();
	}
}
