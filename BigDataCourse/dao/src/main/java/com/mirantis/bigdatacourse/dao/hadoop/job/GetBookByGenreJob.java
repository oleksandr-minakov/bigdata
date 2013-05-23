package com.mirantis.bigdatacourse.dao.hadoop.job;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

import java.util.ArrayList;
import java.util.List;

public class GetBookByGenreJob {
	
	private HadoopConnector hadoop;
	public int querySize = 0;
	public GetBookByGenreJob(HadoopConnector hadoop) {
		this.hadoop = hadoop;
	}

	public List<Book> getBooksBy(int pageNum, int pageSize, String genre) throws DaoException {
		
		List<Book> ret = new ArrayList<Book>();
		
		try{
			GetBookByTokenJob getBooksByToken = new GetBookByTokenJob(this.hadoop);
			querySize = getBooksByToken.querySize;
			ret = getBooksByToken.getBookByToken(pageNum, pageSize, "genre", genre);
			return ret;
		} catch(Exception e) {
			throw new DaoException(e);
		}
	}
}
