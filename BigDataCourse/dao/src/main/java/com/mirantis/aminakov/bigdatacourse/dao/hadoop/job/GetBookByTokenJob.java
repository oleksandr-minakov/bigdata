package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class GetBookByTokenJob {
	
	
	public List<Book> getBookByToken(int pageNum, int pageSize, String token, HadoopConnector hadoop) throws DaoException{
		
		List<FileStatus> statList;
		try {
			statList = Arrays.asList(hadoop.getFS().listStatus(hadoop.getFS().getHomeDirectory()));
			List<Path> pathList = new ArrayList<Path>();
			
			for(FileStatus fStat: statList){
					
				Path lvl0 = fStat.getPath(); // ID
				hadoop.getFS().setWorkingDirectory(lvl0);
				
				Path lvl1 = Arrays.asList(hadoop.getFS().listStatus(hadoop.getFS().getWorkingDirectory())).get(0).getPath(); // Author
				hadoop.getFS().setWorkingDirectory(lvl1);
				
				Path lvl2 = Arrays.asList(hadoop.getFS().listStatus(hadoop.getFS().getWorkingDirectory())).get(0).getPath(); // Genre
				hadoop.getFS().setWorkingDirectory(lvl2);
				
				Path lvl3 = Arrays.asList(hadoop.getFS().listStatus(hadoop.getFS().getWorkingDirectory())).get(0).getPath(); // Title
				hadoop.getFS().setWorkingDirectory(lvl3);
				
				Path lvl4 = Arrays.asList(hadoop.getFS().listStatus(hadoop.getFS().getWorkingDirectory())).get(0).getPath(); // File
				
				if(lvl0.getName().equals(token) || lvl1.getName().equals(token) || lvl2.getName().equals(token) || lvl3.getName().equals(token) ){
					pathList.add(lvl4);
				}
				
			}
			
			if(pathList.size() != 0)
				return new GetBookByPath().getBooksByPathList(pathList.subList((pageNum-1)*pageSize, pageSize*pageNum), hadoop);
			else
				return null;
		} catch (IOException e) {throw new DaoException(e);}
		
	}

}
