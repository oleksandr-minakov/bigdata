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
	
	private HadoopConnector hadoop;
	
	public GetBookByTokenJob(HadoopConnector hadoop){
		
		this.hadoop = hadoop;
	}
	
	public List<Book> getBookByToken(int pageNum, int pageSize, String tokenName, String tokenValue) throws DaoException{
		
		int pos = -1;
		if(tokenName.equals("title")){
		
			pos = 3;
		}
		if(tokenName.equals("author")){
			
			pos = 1;
		}
		if(tokenName.equals("genre")){
			
			pos = 2;
		}
		
		List<FileStatus> statList;
		List<Book> ret = new ArrayList<Book>();
		try {
			String stringPath;
			statList = Arrays.asList(hadoop.getFS().listStatus(new Path(hadoop.workingDirectory)));
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
				
				stringPath = lvl3.toString().substring(hadoop.getURI().length()+1);
				List<String> pathLevels = Arrays.asList(stringPath.split("/"));
				List<String> book = pathLevels.subList(2, pathLevels.size());
				String curToken = book.get(pos);
				if(curToken.equals(tokenValue)){
					pathList.add(lvl3);
				}
			}
			
			if(pathList.size() != 0 && pathList.size() >= pageNum*pageSize){
				ret = new GetBookByPath().getBooksByPathList(pathList.subList((pageNum-1)*pageSize, pageSize*pageNum), hadoop);
				return ret;
			}
			if(pathList.size() >= 0 && pathList.size() < pageNum*pageSize){
				ret = new GetBookByPath().getBooksByPathList(pathList.subList(0, pathList.size()), hadoop);
				return ret;
			}
			else
				return ret;
		} catch (IOException e) {throw new DaoException(e);}
		
	}

}
