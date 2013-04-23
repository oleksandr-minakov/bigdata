package com.mirantis.bigdatacourse.dao.hadoop.job;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class GetBooksCountJob {
	
	private HadoopConnector hadoop;
	
	public GetBooksCountJob(HadoopConnector hadoop) {
		
		this.hadoop = hadoop;
	}
	
	public int getBooksCount() throws DaoException {
		
		try {
			
			List<FileStatus> statList = Arrays.asList(hadoop.getFS().listStatus(new Path(hadoop.workingDirectory)));
			
			return statList.size();
			
		} catch (IOException e) {
			throw new DaoException(e);
			}
	}
}
