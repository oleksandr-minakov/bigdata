package com.mirantis.bigdatacourse.dao.hadoop.job;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

import java.util.ArrayList;
import java.util.List;

public class GetBookByAuthorJob {
	
	private HadoopConnector hadoop;
	
	public GetBookByAuthorJob(HadoopConnector hadoop) {
		this.hadoop = hadoop;
	}

	public List<Book> getBooksBy(int pageNum, int pageSize, String author) throws DaoException {

        List<Book> ret = new ArrayList<Book>();

		try{
			GetBookByTokenJob getBooksbyToken = new GetBookByTokenJob(this.hadoop);
			ret = getBooksbyToken.getBookByToken(pageNum, pageSize, "author", author);
			return ret;
		} catch(Exception e) {
			throw new DaoException(e);
		}
	}
}
