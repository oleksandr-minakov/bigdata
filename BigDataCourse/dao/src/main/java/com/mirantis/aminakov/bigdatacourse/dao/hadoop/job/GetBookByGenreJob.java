package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.util.ArrayList;
import java.util.List;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class GetBookByGenreJob {
private HadoopConnector hadoop;
	
	public GetBookByGenreJob(HadoopConnector hadoop){
		
		this.hadoop = hadoop;
	}
	
	
	public List<Book> getBooksBy(int pageNum, int pageSize, String genre) throws DaoException{
		
		List<Book> ret = new ArrayList<Book>();
		
		try{
			GetBookByTokenJob getBooksbyToken = new GetBookByTokenJob(this.hadoop);
			ret = getBooksbyToken.getBookByToken(pageNum, pageSize, "genre", genre);
			return ret;
		} catch(Exception e){throw new DaoException(e);}
		
	}
}
