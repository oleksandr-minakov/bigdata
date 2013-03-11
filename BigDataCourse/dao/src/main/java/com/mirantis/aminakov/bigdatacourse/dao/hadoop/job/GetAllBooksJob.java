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
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConfiguration;

public class GetAllBooksJob {
	
	public static final Logger LOG = Logger.getLogger(DeleteBookJob.class);
	
	private FileSystem hadoopFs;
	
	public GetAllBooksJob(HadoopConfiguration conf) throws IOException{
		
		this.hadoopFs = conf.getFS();
	}
	
	public List<Book> getAllBooksJob(int pageNum, int pageSize) throws DaoException{
		
		List<FileStatus> statList;
		try {
			statList = Arrays.asList(hadoopFs.listStatus(hadoopFs.getHomeDirectory()));
			List<Path> pathList = new ArrayList<Path>();
			
			for(FileStatus fStat: statList){
					
				Path lvl0 = fStat.getPath(); // ID
				hadoopFs.setWorkingDirectory(lvl0);
				
				lvl0 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // Author
				hadoopFs.setWorkingDirectory(lvl0);
				
				lvl0 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // Genre
				hadoopFs.setWorkingDirectory(lvl0);
				
				lvl0 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // Title
				hadoopFs.setWorkingDirectory(lvl0);
				
				Path lvl4 = Arrays.asList(hadoopFs.listStatus(hadoopFs.getWorkingDirectory())).get(0).getPath(); // File
				
				pathList.add(lvl4);
				
			}
			if(pathList.size() != 0)
				return new GetBookByPath().getBooksByPathList(pathList.subList((pageNum-1)*pageSize, pageSize*pageNum), hadoopFs);
			
		} catch (IOException e) {throw new DaoException(e);}
		
		return null;
	}
}
