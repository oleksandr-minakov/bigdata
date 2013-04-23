package com.mirantis.bigdatacourse.dao.hadoop.job;

import java.util.ArrayList;
import java.util.List;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class GetBookByTitleJob {
	
	private HadoopConnector hadoop;
	public int querySize = 0;
	
	public GetBookByTitleJob(HadoopConnector hadoop) {
		
		this.hadoop = hadoop;
	}
	
	
	public List<Book> getBooksBy(int pageNum, int pageSize, String title) throws DaoException {
		
		List<Book> ret = new ArrayList<Book>();
		
		try{
			GetBookByTokenJob getBooksbyToken = new GetBookByTokenJob(this.hadoop);
			querySize = getBooksbyToken.querySize;
			ret = getBooksbyToken.getBookByToken(pageNum, pageSize, "title", title);
			return ret;
		} catch(Exception e) {
			throw new DaoException(e);
			}
		
	}

}
