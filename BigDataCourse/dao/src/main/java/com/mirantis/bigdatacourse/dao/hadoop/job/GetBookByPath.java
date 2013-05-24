package com.mirantis.bigdatacourse.dao.hadoop.job;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetBookByPath {
	
	public Book getBookByPath(Path path, HadoopConnector hadoop) throws DaoException {
		
		String stringPath = path.toString().substring(hadoop.getURI().length() + 1);
		List<String> pathLevels = Arrays.asList(stringPath.split("/"));
		List<String> book = pathLevels.subList(2, pathLevels.size());

		Book newBook = new Book();
		newBook.setId(book.get(0));
		newBook.setAuthor(book.get(1));
		newBook.setGenre(book.get(2));
		newBook.setTitle(book.get(3));
		
		FSDataInputStream in;
		try {
			in = hadoop.getFS().open(path);
			byte[] toWrite = new byte[in.available()];
			in.read(toWrite);
			newBook.setText(new ByteArrayInputStream(toWrite));
			in.close();
			return newBook;
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}

	public List<Book> getBooksByPathList(List<Path> pathList, HadoopConnector hadoop) throws DaoException {
		List<Book> books = new ArrayList<>();
		for(Path path: pathList){
			try {
				books.add(getBookByPath(path, hadoop));
			} catch (DaoException e) {
				throw new DaoException(e);
			}
		}
		return books;
	}
}