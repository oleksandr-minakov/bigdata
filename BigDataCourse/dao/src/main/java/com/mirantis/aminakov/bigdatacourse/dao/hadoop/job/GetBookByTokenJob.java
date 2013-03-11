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

public class GetBookByTokenJob {
	
	
	public List<Book> getBookByToken(int pageNum, int pageSize, String token, FileSystem hadoopFs) throws DaoException{
		
		List<FileStatus> statList;
		try {
			statList = Arrays.asList(hadoopFs.listStatus(hadoopFs.getHomeDirectory()));
			List<Path> pathList = new ArrayList<Path>();
			
			for(FileStatus fStat: statList){
					
				Path lvl0 = fStat.getPath(); // ID
				hadoopFs.setWorkingDirectory(lvl0);
				
				Path lvl1 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // Author
				hadoopFs.setWorkingDirectory(lvl1);
				
				Path lvl2 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // Genre
				hadoopFs.setWorkingDirectory(lvl2);
				
				Path lvl3 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // Title
				hadoopFs.setWorkingDirectory(lvl3);
				
				Path lvl4 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // File
				
				if(lvl0.getName().equals(token) || lvl1.getName().equals(token) || lvl2.getName().equals(token) || lvl3.getName().equals(token) ){
					pathList.add(lvl4);
				}
				
			}
			
			if(pathList.size() != 0)
				return new GetBookByPath().getBooksByPathList(pathList.subList((pageNum-1)*pageSize, pageSize*pageNum), hadoopFs);
			else
				return null;
		} catch (IOException e) {throw new DaoException(e);}
		
	}

}
