package com.mirantis.bigdatacourse.dao.hadoop.job;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

import java.util.List;

public class GetBookByTextJob {
	
	private HadoopConnector hadoop;
	public int querySize = 0;
	public GetBookByTextJob(HadoopConnector hadoop) {
		this.hadoop = hadoop;
	}

	public List<Book> getBooksBy(int pageNum, int pageSize, String title) throws DaoException {
        List<Book> ret;
		try {
			GetBookByTokenJob getBooksByToken = new GetBookByTokenJob(this.hadoop);
			querySize = getBooksByToken.querySize;
			ret = getBooksByToken.getBookByToken(pageNum, pageSize, "title", title);
			return ret;
		} catch(Exception e) {
			throw new DaoException(e);
		}
	}
}
