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
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConfiguration;

public class GetAllBooksJob {
	
	public static final Logger LOG = Logger.getLogger(DeleteBookJob.class);
	
	private FileSystem hadoopFs;
	
	public GetAllBooksJob(HadoopConfiguration conf) throws IOException{
		
		this.hadoopFs = conf.getFS();
	}
	
	public List<Book> getAllBooksJob(int pageNum, int pageSize) throws IOException{
		
		List<FileStatus> statList = Arrays.asList(hadoopFs.listStatus(hadoopFs.getHomeDirectory()));
		List<Path> pathList = new ArrayList<Path>();
		List<Book> books = new ArrayList<Book>();
		
		if(pageSize > statList.size() || pageNum*pageSize > statList.size() ){
			statList = statList.subList(0, statList.size()-1);
		}
		else{
			statList = statList.subList((pageNum-1)*pageSize, pageNum*pageSize);
		}
		
		for(FileStatus stat: statList){
			pathList.add(stat.getPath());
		}
		
		books = new GetBookByPath().getBooksByPathList(pathList, hadoopFs);
		
		return books;
	}
}
