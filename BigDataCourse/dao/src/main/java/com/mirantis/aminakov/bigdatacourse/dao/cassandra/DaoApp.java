package com.mirantis.aminakov.bigdatacourse.dao.cassandra;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
@SuppressWarnings("unused")

public class DaoApp implements Dao{

	public static final Logger LOG = Logger.getLogger(DaoApp.class);

	private Constants cts;
	
	public DaoApp(Constants cts){
		this.cts = cts;
		cts.bookID= 0;
	}

	@Override
	public int addBook(Book book) throws DaoException {
		
		cts.bookID++;
		book.setId(cts.bookID);
		try{
			Mutator<String> mutator = HFactory.createMutator(cts.getKeyspace(), StringSerializer.get());
			for(HColumn<String, String> col: BookConverter.getInstance().book2row(book))
				mutator.insert("book "+ String.valueOf(book.getId()), cts.CF_NAME, col);
		}catch (Exception e) {throw new DaoException(e);
            }
		return book.getId();
	}

	@Override
	public int delBook(int id) throws DaoException {
		Mutator<String> mutator = HFactory.createMutator(cts.getKeyspace(), StringSerializer.get());
		mutator.delete("book"+String.valueOf(id), cts.CF_NAME, null, StringSerializer.get()); ;
		return 0;
	}

	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DaoException {
		
		List<String> keyStorage = getAllRowKeys();
		
		List<String> neededKeys;
		
		if(pageNum <0 || pageNum > getPageCount(keyStorage, pageSize)){
			return null;
		}
		else{
			
			if(pageNum*pageSize > keyStorage.size()){
				neededKeys = keyStorage.subList((pageNum-1)*pageSize, keyStorage.size());
				return getBooks(neededKeys);
			}
			
			else{
				neededKeys = keyStorage.subList((pageNum-1)*pageSize, pageNum*pageSize);
				return getBooks(neededKeys);
			}
		}
	}

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title)
			throws DaoException {
		
		List<Book> books = getAllBooks(pageNum, pageSize);
		
		List<Book> titledBooks = new ArrayList<Book>();
		
		for(Book book: books){
			
			if(book.getTitle().equals(title)){
				titledBooks.add(book);
			}
		}
		return titledBooks;
	}

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text)
			throws DaoException {
		
		List<Book> books = getAllBooks(pageNum, pageSize);
		
		List<Book> booksByText = new ArrayList<Book>();
		
		for(Book book: books){
			
			try {
				if(book.getReadbleText().equals(text)){
					booksByText.add(book);
				}
			} catch (Exception e) {throw new DaoException(e);}
		}
		return booksByText;
	}

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author)
			throws DaoException {
		List<Book> books = getAllBooks(pageNum, pageSize);
		List<Book> booksByAuthor = new ArrayList<Book>();
		
		for(Book book: books){
			
			if(book.getAuthor().equals(author)){
				booksByAuthor.add(book);
			}
		}
		return booksByAuthor;
	}

	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre)
			throws DaoException {
		List<Book> books = getAllBooks(pageNum, pageSize);
		List<Book> booksByGenre = new ArrayList<Book>();
		
		for(Book book: books){
			if(book.getGenre().equals(genre)){
				booksByGenre.add(book);
			}
		}
		return booksByGenre;
	}

	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException {
		
		List<Book> books = getAllBooks(pageNum, pageSize);
		List<Book> authorByGenre = new ArrayList<Book>();
		TreeSet<String> authors = new TreeSet<String>();
		for(Book book: books){
			if(book.getGenre().equals(genre)){
				authors.add(book.getAuthor());
			}
		}
		return authors;
	}

	@Override
	public void closeConnection() throws DaoException {
		cts.getCurrentClstr().getConnectionManager().shutdown();
		
	}
	
	public List<String> getAllRowKeys(){
		

		List<String> pagedBooks = new ArrayList<String>();
		
		RangeSlicesQuery<String, String, String> books = HFactory.createRangeSlicesQuery(cts.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get()); 
		books.setColumnFamily(cts.CF_NAME);
		books.setKeys("", "");
		books.setReturnKeysOnly();
		
		QueryResult<OrderedRows<String, String, String>> result = books.execute();
        OrderedRows<String, String, String> orderedRows = result.get();
        Iterator<Row<String, String, String>> it = orderedRows.getList().iterator();
        while(it.hasNext()){
        	pagedBooks.add(it.next().getKey());
        }        
		return pagedBooks;
	}

	public int getPageCount(List<String> keys, int pageSize){
		
		int pages = keys.size()/pageSize;
		if(keys.size()%pageSize != 0)
			return pages+1;
		else
			return pages;
	}
	
	public List<Book> getBooks(List<String> rowKeys){
		
		List<Book> booksByKeys = new ArrayList<Book>();
		RangeSlicesQuery<String, String, String> books = HFactory.createRangeSlicesQuery(cts.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get()); 
		books.setColumnFamily(cts.CF_NAME);
		books.setKeys(rowKeys.get(0), rowKeys.get(rowKeys.size()-1));
		books.setRange("", "", false, 5);
		books.setRowCount(rowKeys.size());
		
		QueryResult<OrderedRows<String, String, String>> result = books.execute();
        OrderedRows<String, String, String> orderedRows = result.get();
        for(Row<String, String, String> row:orderedRows.getList()){
        	booksByKeys.add(BookConverter.getInstance().row2book(row.getColumnSlice().getColumns()));
        }        
		return booksByKeys;
	}

	@Override
	public int getNumberOfRecords() {
		return getAllRowKeys().size();
	}
	
}
