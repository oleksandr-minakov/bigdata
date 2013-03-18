package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

<<<<<<< HEAD
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
=======
import java.io.IOException;
>>>>>>> macbranch

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
<<<<<<< HEAD
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConfiguration;
=======
import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
>>>>>>> macbranch
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;


public class AddBookJob {
	
	public static final Logger LOG = Logger.getLogger(AddBookJob.class);
	
<<<<<<< HEAD
	private FileSystem hadoopFs;
	
	public AddBookJob(HadoopConfiguration conf) throws IOException{
		
		this.hadoopFs = conf.getFS();
	}
	
	public int addBookJob(Book book) throws IOException{
		
		String dest = new PathFormer().formAddPath(book, hadoopFs.getHomeDirectory().toString());
		
		Path path = new Path(dest);
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
        
		return book.getId();
=======
	private HadoopConnector hadoopConf;
	
	public AddBookJob(HadoopConnector conf) throws DaoException{
		
		this.hadoopConf = conf;
	}
	
	public int addBookJob(Book book) throws DaoException{
		
		FileSystem fs = hadoopConf.getFS();
		String dest = new PathFormer().formAddPath(book, hadoopConf.workingDirectory);
		
		Path path = new Path(dest);
		try {

	        FSDataOutputStream out = fs.create(path);
	        
	        out.write(book.getReadbleText().getBytes());	        
	        out.close();
	        
	        return book.getId();
	        
		} catch (IOException e) {throw new DaoException(e);}
>>>>>>> macbranch
	}
}
