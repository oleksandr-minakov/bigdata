package com.mirantis.aminakov.bigdatacourse;

import java.util.List;

public class ServiceToDao implements Service {

	@Override
	public int addBook(Book book) {
		int ret = 0;
		try {
			Dao dao = new DaoJdbc();
			ret = dao.addBook(book);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List<Book> findByAuthor(int pageNum, int pageSize, String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findByTitle(int pageNum, int pageSize, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findByText(int pageNum, int pageSize, String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findByGenre(int pageNum, int pageSize, String genre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAuthorByGenre(int pageNum, int pageSize,
			String genre) {
		// TODO Auto-generated method stub
		return null;
	}

}
