package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileSystem;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class GetBookByTextJob {
	private HadoopConnector hadoop;
	
	public GetBookByTextJob(HadoopConnector hadoop){
		
		this.hadoop = hadoop;
	}
	
	
	public List<Book> getBooksBy(int pageNum, int pageSize, String title) throws DaoException{
		
		List<Book> ret = new ArrayList<Book>();
		
		try{
			GetBookByTokenJob getBooksbyToken = new GetBookByTokenJob(this.hadoop);
			ret = getBooksbyToken.getBookByToken(pageNum, pageSize, "title", title);
			return ret;
		} catch(Exception e){throw new DaoException(e);}
		
	}

}
