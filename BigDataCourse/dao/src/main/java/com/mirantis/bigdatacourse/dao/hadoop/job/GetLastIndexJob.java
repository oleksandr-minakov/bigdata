package com.mirantis.bigdatacourse.dao.hadoop.job;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetLastIndexJob {
	
	private HadoopConnector hadoop;
	
	public GetLastIndexJob(HadoopConnector hadoop) throws DaoException {
		this.hadoop = hadoop;
		
	}
	
	public int getLastIndex() throws DaoException {
		
		int last = 0;
		
		try {
			List<FileStatus> fsList = new ArrayList<FileStatus>();
			fsList = Arrays.asList(hadoop.getFS().listStatus(new Path(hadoop.workingDirectory)));
			
			if(fsList.size() == 0)
				return 0;
			
			List<String> stringIDs = new ArrayList<String>();
			
			String[] parsedIDs = new String[fsList.size()];
			
			List<String> pathLevels = new ArrayList<String>();
			for(FileStatus fs: fsList) {
				pathLevels = Arrays.asList(fs.getPath().toString().split("/"));
				List<String> book = pathLevels.subList(5, pathLevels.size());
				if(book.size() == 0 )
					return 0;
				stringIDs.add(book.get(0));
			}
			
			stringIDs.toArray(parsedIDs);
			int[] IDs = new int[parsedIDs.length];
			
			for(int i = 0; i < parsedIDs.length; ++i) {
				IDs[i] = Integer.valueOf(parsedIDs[i]);
			}
			Arrays.sort(IDs);
			last = IDs[IDs.length - 1];
			return last;
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}
	
	public int getIncrementedNewID() throws DaoException {
		return getLastIndex() + 1;
	}
}
