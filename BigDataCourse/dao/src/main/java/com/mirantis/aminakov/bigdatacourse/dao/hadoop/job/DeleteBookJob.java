package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConfiguration;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;

public class DeleteBookJob {

	public static final Logger LOG = Logger.getLogger(DeleteBookJob.class);
	
	private FileSystem hadoopFs;
	
	public DeleteBookJob(HadoopConfiguration conf) throws IOException{
		
		this.hadoopFs = conf.getFS();
	}
	
	public int deleteBookJob(int id) throws IOException{
		
		String dest = new PathFormer().formDeletePath(hadoopFs.getHomeDirectory().toString(), id);
		
		Path path = new Path(dest);
		
        if (!hadoopFs.exists(path)) {
            LOG.debug("Parent folder " + hadoopFs.getHomeDirectory().toString() + "/" + id + "/... doesn't already exists");
            return -1;
        }
        
		hadoopFs.delete(path, true);
		
        hadoopFs.close();
        
		return id;
	}
}
