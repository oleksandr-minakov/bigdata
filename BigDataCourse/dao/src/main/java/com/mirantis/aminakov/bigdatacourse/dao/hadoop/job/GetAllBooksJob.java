package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.Book;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class GetAllBooksJob {
	
	public static final Logger LOG = Logger.getLogger(DeleteBookJob.class);
	private HadoopConnector hadoopConf;
	
	public GetAllBooksJob(HadoopConnector hadoopConf){
		this.hadoopConf = hadoopConf;
	}
		
	public List<Book> getAllBooksJob(int pageNum, int pageSize) throws DaoException{
		
		Path wDir = new Path(hadoopConf.workingDirectory);
		FileSystem fs = this.hadoopConf.getFS();
		List<FileStatus> statList;
		try {
			
			statList = Arrays.asList(fs.listStatus(wDir));
			List<Path> pathList = new ArrayList<Path>();
			
			for(FileStatus fStat: statList){
					
				Path lvl0 = fStat.getPath(); // ID
				fs.setWorkingDirectory(lvl0);
				
				lvl0 = Arrays.asList(fs.listStatus(fs.getWorkingDirectory())).get(0).getPath(); // Author
				fs.setWorkingDirectory(lvl0);
				
				lvl0 = Arrays.asList(fs.listStatus(fs.getWorkingDirectory())).get(0).getPath(); // Genre
				fs.setWorkingDirectory(lvl0);
				
				lvl0 = Arrays.asList(fs.listStatus(fs.getWorkingDirectory())).get(0).getPath(); // Title
				fs.setWorkingDirectory(lvl0);
				
				Path lvl4 = Arrays.asList(fs.listStatus(fs.getWorkingDirectory())).get(0).getPath(); // File
				
				pathList.add(lvl4);
				
			}
			if(pathList.size() != 0)
				return new GetBookByPath().getBooksByPathList(pathList.subList((pageNum-1)*pageSize, pageSize*pageNum), fs);
			
		} catch (IOException e) {throw new DaoException(e);}
		
		return null;
	}
}
