package com.mirantis.bigdatacourse.dao.hadoop.job;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.PathFormer;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteBookJob {

	public static final Logger LOG = Logger.getLogger(DeleteBookJob.class);
	private HadoopConnector hadoopConf;
	
	public DeleteBookJob(HadoopConnector conf) throws DaoException {
		this.hadoopConf = conf;
	}
	
	public int deleteBookJob(String id) throws DaoException {
		
		FileSystem fs = this.hadoopConf.getFS();
		LOG.debug("Getting FileSystem ...");
		String destination = new PathFormer().formDeletePath(this.hadoopConf.workingDirectory, id);
		Path path = new Path(destination);
		
		try {
			LOG.debug("Deleting path and included files recursively ...");
			fs.delete(path, true);
		} catch (IOException e) {
			throw new DaoException(e);
		}
        return 0;
	}
}
