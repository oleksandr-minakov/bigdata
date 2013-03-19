package com.mirantis.aminakov.bigdatacourse.dao.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import org.springframework.beans.factory.annotation.Autowired;

public class DaoCassandra implements Dao{

    @Autowired
	private Constants constants;

    private int querySize;
    public static final Logger LOG = Logger.getLogger(DaoCassandra.class);

    public DaoCassandra(Constants constants) throws DaoException{
		this.constants = constants;
		try {
			constants.bookID= getAllRowKeys().size();
		} catch (DaoException e) {throw new DaoException(e);}
	}

	@Override
	public int addBook(Book book) throws DaoException {

		constants.bookID++;
		book.setId(constants.bookID);
		try{
			Mutator<String> mutator = HFactory.createMutator(constants.getKeyspace(), StringSerializer.get());
			for(HColumn<String, String> col: BookConverter.getInstance().book2row(book))
				mutator.insert("book "+ String.valueOf(book.getId()), constants.CF_NAME, col);
		}catch (Exception e) {throw new DaoException(e);
            }
		return book.getId();
	}

	@Override
	public int delBook(int id) throws DaoException {
		
		Mutator<String> mutator = HFactory.createMutator(constants.getKeyspace(), StringSerializer.get());
		try{
			mutator.delete("book "+ String.valueOf(id), constants.CF_NAME, null, StringSerializer.get());
		}catch(Exception e){throw new DaoException(e);}
		
		return 0;
	}

	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DaoException {

		List<String> keyStorage = getAllRowKeys();
		List<Book> ret = new ArrayList<Book>();
		List<String> neededKeys = new ArrayList<String>();

		if(pageNum <0 || pageNum > getPageCount(keyStorage.size(), pageSize)){
			return ret;
		}
		else{

			if(pageNum*pageSize > keyStorage.size()){
				for(Book book:getBooks(keyStorage)){
					if(!book.equals(null) && book.getId() != 0){
						ret.add(book);
					}
				}
				this.querySize = ret.size();	
				return ret;
			}
			else{
				neededKeys = keyStorage.subList((pageNum-1)*pageSize, pageNum*pageSize);
				for(Book book:getBooks(neededKeys)){
					
					if(!book.equals(null) && book.getId() != 0){
						ret.add(book);
					}
				}
				this.querySize = ret.size();	
				return ret;
			}
		}
	}

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title)
			throws DaoException {

		List<Book> lst  = getBooksByToken(title ,"book title");
		if(pageNum*pageSize > lst.size())
			return lst.subList((pageNum-1)*pageSize, lst.size());
		else
			return lst.subList((pageNum-1)*pageSize, pageNum*pageSize);
	}

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text)
			throws DaoException {

		List<Book> lst  = getBooksByToken(text ,"book text");
		if(pageNum*pageSize > lst.size())
			return lst.subList((pageNum-1)*pageSize, lst.size());
		else
			return lst.subList((pageNum-1)*pageSize, pageNum*pageSize);
	}

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author)
			throws DaoException {

		List<Book> lst  = getBooksByToken(author ,"book author");
		if(pageNum*pageSize > lst.size())
			return lst.subList((pageNum-1)*pageSize, lst.size());
		else
			return lst.subList((pageNum-1)*pageSize, pageNum*pageSize);
	}

	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre)
			throws DaoException {

		List<Book> lst  = getBooksByToken(genre ,"book genre");
		if(pageNum*pageSize > lst.size())
			return lst.subList((pageNum-1)*pageSize, lst.size());
		else
			return lst.subList((pageNum-1)*pageSize, pageNum*pageSize);
	}

	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException {

		List<Book> lst= getBookByGenre(pageNum, pageSize, genre);
		TreeSet<String> tree= new TreeSet<String>();
		for(Book book: lst){
			tree.add(book.getAuthor());
		}
		return tree;

	}

	@Override
	public void closeConnection() throws DaoException {
		constants.getCurrentClstr().getConnectionManager().shutdown();

	}

	public List<String> getAllRowKeys() throws DaoException{

		List<String> pagedBooks = new ArrayList<String>();

		RangeSlicesQuery<String, String, String> books = HFactory.createRangeSlicesQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		books.setColumnFamily(constants.CF_NAME);
		books.setKeys("", "");
		books.setReturnKeysOnly();
		books.setRowCount(Integer.MAX_VALUE-1);
		try{
			QueryResult<OrderedRows<String, String, String>> result = books.execute();
		
			OrderedRows<String, String, String> orderedRows = result.get();
        	List<Row<String, String, String>> keys = orderedRows.getList();
        	for(Row<String, String, String> row: keys){
        		pagedBooks.add(row.getKey());
        	}
		} catch (Exception e){throw new DaoException(e);}
        this.querySize = pagedBooks.size();
        
		return pagedBooks;
	}

	public int getPageCount(int amountOfRecords, int pageSize){

		int pages = amountOfRecords/pageSize;
		if(amountOfRecords%pageSize != 0)
			return pages+1;
		else
			return pages;
	}

	public List<Book> getBooks(List<String> rowKeys) throws DaoException{

		List<Book> booksByKeys = new ArrayList<Book>();

		MultigetSliceQuery<String, String, String> books =HFactory.createMultigetSliceQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		books.setColumnFamily(constants.CF_NAME);
		books.setKeys(rowKeys);
		books.setRange("", "", false, 5);
		try{
		QueryResult<Rows<String, String, String>> result = books.execute();
        Rows<String, String, String> orderedRows = result.get();
        for(Row<String, String, String> row:orderedRows){
   			booksByKeys.add(BookConverter.getInstance().row2book(row.getColumnSlice().getColumns()));
        }
		return booksByKeys;
		}catch (Exception e){throw new DaoException(e);}
	}

	public List<Book> getBooksByToken(String lookFor, String token) throws DaoException{

		List<String> keys = getAllRowKeys();
		List<String> neededKeys = new ArrayList<String>();
		ColumnQuery<String, String, String> columnQuery;
		QueryResult<HColumn<String, String>> result;
		columnQuery = HFactory.createStringColumnQuery(constants.getKeyspace());
		columnQuery.setColumnFamily(constants.CF_NAME).setName(token);

		for(String key: keys){

			columnQuery.setKey(key);
			result = columnQuery.execute();
			HColumn<String, String> col= result.get();
			if(lookFor.equals(col.getValue())){
				neededKeys.add(key);
			}
		}
		this.querySize = neededKeys.size();
		if(this.querySize == 0){
			
			neededKeys.add(null);
			return getBooks(neededKeys);
		}
		
		return getBooks(neededKeys);
	}

	@Override
	public int getNumberOfRecords() {

		return this.querySize;
	}

}
