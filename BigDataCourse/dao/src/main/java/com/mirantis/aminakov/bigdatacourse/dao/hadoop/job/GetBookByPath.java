package com.mirantis.aminakov.bigdatacourse.dao.hadoop.job;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;

public class GetBookByPath {
	
	
	public Book getBookByPath(Path path, FileSystem hadoop) throws DaoException{
		
		String stringPath = path.toString();
		List<String> pathLevels = Arrays.asList(stringPath.split("/"));
		
		List<String> book = pathLevels.subList(2, pathLevels.size());
		
		Book newBook = new Book();
		newBook.setId(Integer.valueOf(book.get(0)));
		newBook.setAuthor(book.get(1));
		newBook.setGenre(book.get(2));
		newBook.setTitle(book.get(3));
		
		FSDataInputStream in;
		try {
			in = hadoop.open(path);
			byte[] toWrite = new byte[in.available()];
			in.read(toWrite);
			newBook.setText(new ByteArrayInputStream(toWrite));
			return newBook;
			
		} catch (IOException e) {throw new DaoException(e);}
	}
	
	
	public List<Book> getBooksByPathList(List<Path> pathList, FileSystem hadoop) throws DaoException{
		
		List<Book> books = new ArrayList<Book>();
		for(Path path: pathList){
			try {
				books.add(getBookByPath(path, hadoop));
			} catch (DaoException e) {throw new DaoException(e);}
		}
		return books;
	}
	

}
