package com.mirantis.aminakov.bigdatacourse.dao1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.BasicKeyspaceDefinition;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

public class DAOApp implements DAO{

	private Cluster clstr;
	private Keyspace ksOper;
	private BasicColumnFamilyDefinition CfDef;
	private int bookID;
	
	public DAOApp(){
		
		clstr = HFactory.getOrCreateCluster(Constants.CLUSTER_NAME, Constants.HOST_DEF+":9160");
		
		BasicKeyspaceDefinition KsDef = new BasicKeyspaceDefinition();
		KsDef.setName(Constants.KEYSPACE_NAME);
		KsDef.setDurableWrites(true);
		KsDef.setReplicationFactor(1);
		KsDef.setStrategyClass("org.apache.cassandra.locator.SimpleStrategy");
		clstr.addKeyspace(KsDef, true);
		ksOper = HFactory.createKeyspace(Constants.KEYSPACE_NAME, clstr);
				
		CfDef = new BasicColumnFamilyDefinition();
		CfDef.setKeyspaceName(Constants.KEYSPACE_NAME);
		CfDef.setName(Constants.CF_NAME);
        clstr.addColumnFamily(CfDef);
        
        
        bookID= 0;
	}

	@Override
	public int addBook(Book book) throws DAOException {
		bookID++;
		book.setId(bookID);
		try{
			Mutator<String> mutator = HFactory.createMutator(ksOper, StringSerializer.get());
			for(HColumn<String, String> col: BookConverter.getInstance().book2row(book))
				mutator.insert("book "+ String.valueOf(book.getId()), Constants.CF_NAME, col);
		}catch (HectorException | IOException e) {
            e.printStackTrace();}
		return book.getId();
	}

	@Override
	public int delBook(int id) throws DAOException {
		Mutator<String> mutator = HFactory.createMutator(ksOper, StringSerializer.get());
		mutator.delete("book"+String.valueOf(id), Constants.CF_NAME, null, StringSerializer.get()); ;
		return 0;
	}

	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize)
			throws DAOException {
		
		List<String> keys = getAllRowKeys();
		if(pageNum <0 ||pageNum > getPageCount(pageSize)){
			return null;
		}
		else{
			if(pageNum*pageSize > keys.size()){
				List<String> neededKeys = getAllRowKeys().subList((pageNum-1)*pageSize, keys.size());
				return getBooks(neededKeys);
			}
			else{
				List<String> neededKeys = getAllRowKeys().subList((pageNum-1)*pageSize, pageNum*pageSize);
				return getBooks(neededKeys);
			}
		}
	}

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title)
			throws DAOException {
		List<Book> books = getAllBooks(pageNum, pageSize);
		List<Book> titledBooks = new ArrayList<Book>();
		
		for(Book book: books){
			
			if(book.getText().equals(title)){
				titledBooks.add(book);
			}
		}
		return titledBooks;
	}

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text)
			throws DAOException {
		List<Book> books = getAllBooks(pageNum, pageSize);
		List<Book> booksByText = new ArrayList<Book>();
		
		for(Book book: books){
			
			if(book.getAuthor().equals(text)){
				booksByText.add(book);
			}
		}
		return booksByText;
	}

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author)
			throws DAOException {
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
			throws DAOException {
		List<Book> books = getAllBooks(pageNum, pageSize);
		List<Book> booksByGenre = new ArrayList<Book>();
		
		for(Book book: books){
			if(book.getGenre().equals(genre)){
				booksByGenre.add(book);
			}
		}
		return booksByGenre;
	}

	@SuppressWarnings("unused")
	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DAOException {
		List<Book> books = getAllBooks(pageNum, pageSize);
		List<Book> authorByGenre = new ArrayList<Book>();
		TreeSet<String> authors = new TreeSet<String>();
		for(Book book: books){
			if(book.getGenre().equalsIgnoreCase(genre)){
				authors.add(book.getAuthor());
			}
		}
		return authors;
	}

	@Override
	public void closeConnection() throws DAOException {
		clstr.getConnectionManager().shutdown();
		
	}
	
	public List<String> getAllRowKeys(){
		

		List<String> pagedBooks = new ArrayList<String>();
		
		RangeSlicesQuery<String, String, String> books = HFactory.createRangeSlicesQuery(ksOper, StringSerializer.get(), StringSerializer.get(), StringSerializer.get()); 
		books.setColumnFamily(CfDef.getName());
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

	public int getPageCount(int pageSize){
		
		int pages = getAllRowKeys().size()/pageSize;
		if(getAllRowKeys().size()%pageSize != 0)
			return pages+1;
		else
			return pages;
	}
	
	public List<Book> getBooks(List<String> rowKeys){
		
		List<Book> booksByKeys = new ArrayList<Book>();
		RangeSlicesQuery<String, String, String> books = HFactory.createRangeSlicesQuery(ksOper, StringSerializer.get(), StringSerializer.get(), StringSerializer.get()); 
		books.setColumnFamily(CfDef.getName());
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
	
}
