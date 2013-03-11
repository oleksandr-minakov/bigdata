package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConfiguration;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;

public class DeleteBookJob {

	public static final Logger LOG = Logger.getLogger(DeleteBookJob.class);
	
	private FileSystem hadoopFs;
	
	public DeleteBookJob(HadoopConfiguration conf) throws DaoException{
		
		try {
			this.hadoopFs = conf.getFS();
		} catch (IOException e) {throw new DaoException(e);}
	}
	
	public int deleteBookJob(int id) throws DaoException{
		
		String dest = new PathFormer().formDeletePath(hadoopFs.getHomeDirectory().toString(), id);
		
		Path path = new Path(dest);
		
        try {
			if (!hadoopFs.exists(path)) {
			    LOG.debug("Parent folder " + hadoopFs.getHomeDirectory().toString() + "/" + id + "/... doesn't already exists");
			    return -1;
			}
		       
			hadoopFs.delete(path, true);
			
	        hadoopFs.close();
	        
		} catch (IOException e) {throw new DaoException(e);}
        
        return id;
	}
}
