package com.mirantis.bigdatacourse.dao.hadoop.job;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.PathFormer;

public class DeleteBookJob {

	public static final Logger LOG = Logger.getLogger(DeleteBookJob.class);

	private HadoopConnector hadoopConf;
	
	public DeleteBookJob(HadoopConnector conf) throws DaoException{
		
		this.hadoopConf = conf;
	}
	
	public int deleteBookJob(int id) throws DaoException{
		
		FileSystem fs = this.hadoopConf.getFS();
		LOG.debug("Getting FileSystem ...");
		String dest = new PathFormer().formDeletePath(this.hadoopConf.workingDirectory, id);
		
		Path path = new Path(dest);
		
		try {
			
			LOG.debug("Deleting path and included files recursively ...");
			fs.delete(path, true);
			
		} catch (IOException e){throw new DaoException(e);}
		
        return 0;
	}
}
