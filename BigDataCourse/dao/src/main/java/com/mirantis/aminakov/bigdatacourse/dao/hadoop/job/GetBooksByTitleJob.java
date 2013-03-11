package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;

import com.mirantis.aminakov.bigdatacourse.dao.Book;

public class GetBooksByTitleJob {
	
	
	public List<Book> getBooksByTitle(int pageNum, int pageSize, String title, FileSystem hadoopFs){
		
//		List<FileStatus> statList = Arrays.asList(hadoopFs.listStatus(hadoopFs.getHomeDirectory()));
//		String stringPath = path.toString();
//		List<String> pathLevels = Arrays.asList(stringPath.split("/"));
		
//		List<String> book = pathLevels.subList(2, pathLevels.size());
		
		return null;
	}

}
