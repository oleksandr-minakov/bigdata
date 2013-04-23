package com.mirantis.bigdatacourse.dao.cassandra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;

import me.prettyprint.cassandra.connection.HClientPool;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
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

    public DaoCassandra(Constants constants) throws DaoException {
		this.constants = constants;
		LOG.debug("Connection was established");
		try {
			constants.bookID= getMaxIndex()+1;
			LOG.debug("Getting new ID");
		} catch (DaoException e) {
			LOG.debug(e.getMessage());
            throw new DaoException(e);
        }
	}

	@Override
	public int addBook(Book book) throws DaoException {

		book.setId(constants.bookID);
		try{
			Mutator<String> mutator = HFactory.createMutator(constants.getKeyspace(), StringSerializer.get());
			LOG.debug("Creating new mutator");
			for(HColumn<String, String> col: BookConverter.getInstance().book2row(book))
				mutator.insert("book "+ String.valueOf(book.getId()), constants.CF_NAME, col);
			LOG.debug("Perfoming insertion...");
		}catch (Exception e) {
			LOG.debug(e.getMessage());
            throw new DaoException(e);
        }
		constants.bookID++;
		LOG.info("Book was added, id:" + String.valueOf(book.getId()));
		return book.getId();
	}

	@Override
	public int delBook(int id) throws DaoException {
		
		Mutator<String> mutator = HFactory.createMutator(constants.getKeyspace(), StringSerializer.get());
		LOG.debug("Creating new mutator");
		try{
			mutator.delete("book "+ String.valueOf(id), constants.CF_NAME, null, StringSerializer.get());
			LOG.debug("Book was deleted, id:" + String.valueOf(id));
		}catch(Exception e){throw new DaoException(e);}
		LOG.info("Book was deleted, id:" + String.valueOf(id));
		
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
				LOG.info("Getting all books with pagination");
				LOG.debug("Getting all books with pagination");
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
				LOG.info("Getting all books with pagination");
				LOG.debug("Getting all books with pagination");
				this.querySize = ret.size();
				return ret;
			}
		}
	}

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title)
			throws DaoException {

		List<Book> lst  = getBooksByToken(title ,"book title");
		List<Book> lstCut = new ArrayList<Book>();
		
		if(lst.size() > pageSize*pageNum)
			lstCut =  lst.subList((pageNum-1)*pageSize, pageNum*pageSize);
		
		if(lst.size() > pageSize*(pageNum-1) && pageNum*pageSize >= lst.size())
			lstCut = lst.subList(pageSize*(pageNum-1), lst.size());
		
		if(lst.size() < pageSize && lst.size() <= pageNum*pageSize)
			lstCut = lst.subList(0, lst.size());
		
		if(lst.size() ==0)
			lstCut = lst;
		LOG.info("Getting books with pagination and title: " + title);
		LOG.debug("Getting books with pagination and title: " + title);
		return lstCut;
	}

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text)
			throws DaoException {

		List<Book> lst  = getBooksByToken(text ,"book text");
		List<Book> lstCut = new ArrayList<Book>();

        if(lst.size() > pageSize*pageNum)
            lstCut =  lst.subList((pageNum-1)*pageSize, pageNum*pageSize);

        if(lst.size() > pageSize*(pageNum-1) && pageNum*pageSize >= lst.size())
            lstCut = lst.subList(pageSize*(pageNum-1), lst.size());

        if(lst.size() < pageSize && lst.size() <= pageNum*pageSize)
            lstCut = lst.subList(0, lst.size());
		
		if(lst.size() ==0)
			lstCut = lst;
		LOG.info("Getting books with pagination and text: " + text);
		LOG.debug("Getting books with pagination and text: " + text);
		return lstCut;
	}

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author)
			throws DaoException {

		List<Book> lst  = getBooksByToken(author ,"book author");
		List<Book> lstCut = new ArrayList<Book>();

        if(lst.size() > pageSize*pageNum)
            lstCut =  lst.subList((pageNum-1)*pageSize, pageNum*pageSize);

        if(lst.size() > pageSize*(pageNum-1) && pageNum*pageSize >= lst.size())
            lstCut = lst.subList(pageSize*(pageNum-1), lst.size());

        if(lst.size() < pageSize && lst.size() <= pageNum*pageSize)
            lstCut = lst.subList(0, lst.size());
		
		if(lst.size() ==0)
			lstCut = lst;
		LOG.info("Getting books with pagination and author: " + author);
		LOG.debug("Getting books with pagination and author: " + author);
		return lstCut;
	}

	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre)
			throws DaoException {

		List<Book> lst  = getBooksByToken(genre ,"book genre");
		List<Book> lstCut = new ArrayList<Book>();

        if(lst.size() > pageSize*pageNum)
            lstCut =  lst.subList((pageNum-1)*pageSize, pageNum*pageSize);

        if(lst.size() > pageSize*(pageNum-1) && pageNum*pageSize >= lst.size())
            lstCut = lst.subList(pageSize*(pageNum-1), lst.size());

        if(lst.size() < pageSize && lst.size() <= pageNum*pageSize)
            lstCut = lst.subList(0, lst.size());
		
		if(lst.size() ==0)
			lstCut = lst;
		LOG.info("Getting books with pagination and genre: " + genre);
		LOG.debug("Getting books with pagination and genre: " + genre);
		return lstCut;
	}

	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize,
			String genre) throws DaoException {

		List<Book> lst= getBookByGenre(pageNum, pageSize, genre);
		List<Book> lstCut = new ArrayList<Book>();

        if(lst.size() > pageSize*pageNum)
            lstCut =  lst.subList((pageNum-1)*pageSize, pageNum*pageSize);

        if(lst.size() > pageSize*(pageNum-1) && pageNum*pageSize >= lst.size())
            lstCut = lst.subList(pageSize*(pageNum-1), lst.size());

        if(lst.size() < pageSize && lst.size() <= pageNum*pageSize)
            lstCut = lst.subList(0, lst.size());
		
		if(lst.size() ==0)
			lstCut = lst;
		
		TreeSet<String> tree= new TreeSet<String>();
		for(Book book: lstCut){
			tree.add(book.getAuthor());
		}
		LOG.debug("Getting books authors with pagination and genre: " + genre);
		LOG.info("Getting books authors with pagination and genre: " + genre);
		return tree;

	}

	@Override
	public void closeConnection() throws DaoException {
		constants.getCurrentClstr().getConnectionManager().shutdown();

	}

	public List<String> getAllRowKeys() throws DaoException {

		List<String> pagedBooks = new ArrayList<String>();

		RangeSlicesQuery<String, String, String> books = HFactory.createRangeSlicesQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		books.setColumnFamily(constants.CF_NAME);
		books.setKeys("", "");
		books.setReturnKeysOnly();
		books.setRowCount(Integer.MAX_VALUE);
		LOG.debug("Forming new RangeSlicesQuery<String, String, String>");
		try{
			QueryResult<OrderedRows<String, String, String>> result = books.execute();
			LOG.debug("Executing RangeSlicesQuery<String, String, String>");
			OrderedRows<String, String, String> orderedRows = result.get();
        	List<Row<String, String, String>> keys = orderedRows.getList();
        	for(Row<String, String, String> row: keys){
        		pagedBooks.add(row.getKey());
        	}
        	LOG.debug("Collection row keys...");
		} catch (Exception e){throw new DaoException(e);}
		
        this.querySize = pagedBooks.size();
        LOG.info("Getting all row keys");
		return pagedBooks;
	}

	public int getPageCount(int amountOfRecords, int pageSize){

		int pages = amountOfRecords/pageSize;
		if(amountOfRecords%pageSize != 0)
			return pages+1;
		else
			return pages;
	}

	public List<Book> getBooks(List<String> rowKeys) throws DaoException {

		List<Book> booksByKeys = new ArrayList<Book>();

		MultigetSliceQuery<String, String, String> books =HFactory.createMultigetSliceQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		books.setColumnFamily(constants.CF_NAME);
		books.setKeys(rowKeys);
		books.setRange("", "", false, 5);
		LOG.debug("Forming new RangeSlicesQuery<String, String, String>");
		try{
		QueryResult<Rows<String, String, String>> result = books.execute();
		LOG.debug("Executing RangeSlicesQuery<String, String, String>");
        Rows<String, String, String> orderedRows = result.get();
        for(Row<String, String, String> row:orderedRows){
   			booksByKeys.add(BookConverter.getInstance().row2book(row.getColumnSlice().getColumns()));
        }
        LOG.debug("Collection books...");
		return booksByKeys;
		}catch (Exception e){throw new DaoException(e);}
	}

	public List<Book> getBooksByToken(String lookFor, String token) throws DaoException {

		List<String> keys = getAllRowKeys();
		LOG.debug("Getting all row keys...");
		List<String> neededKeys = new ArrayList<String>();
		List<Book> ret = new ArrayList<Book>();
		ColumnQuery<String, String, String> columnQuery;
		QueryResult<HColumn<String, String>> result;
		columnQuery = HFactory.createStringColumnQuery(constants.getKeyspace());
		columnQuery.setColumnFamily(constants.CF_NAME).setName(token);
		LOG.debug("Forming new ColumnQuery<String, String, String>");
		for(String key: keys){

			columnQuery.setKey(key);
			result = columnQuery.execute();
			LOG.debug("Executing ColumnQuery<String, String, String> for each key");
			HColumn<String, String> col= result.get();
			
			if(col != null){
				if(lookFor.equals(col.getValue())){
					neededKeys.add(key);
				}
			}
		}
		
		ret = getBooks(neededKeys);
		LOG.debug("Getting books by collected keys");
		this.querySize = neededKeys.size();
		LOG.info("Returning books by specific token: " + token + "and value to look for: " + lookFor);
		
		return ret;
	}

	@Override
	public int getNumberOfRecords() {

		try {
			return getAllRowKeys().size();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		LOG.info("Returning query size ...");
		return this.querySize;
	}

	public int getMaxIndex() throws DaoException {

    	int max = 0;
		List<String> keys = getAllRowKeys();
		LOG.debug("Collecting keys...");
		if(keys.size() != 0 ){
			
        	List<String> bookIDs = new ArrayList<String>();
        
        	int[] ids = new int[keys.size()];
        	String[] stringIDs = new String[keys.size()];
        
        	for(String key:keys){
        		bookIDs.add(key.substring("book ".length()));
        	}
        
        	bookIDs.toArray(stringIDs);
        
        	for(int i = 0; i < bookIDs.size(); ++i){
        	
        		ids[i] = Integer.valueOf(stringIDs[i]);
        	}
        	Arrays.sort(ids);
        	max = ids[ids.length-1];
        	LOG.debug("Getting max index...");
		}
		LOG.info("Returning max index");
        return max;
    }

	@Override
	public void afterPropertiesSet() throws DaoException {
		
		LOG.debug("Checking DaoCassandra bean");
		if(this.constants == null)
			throw new DaoException("Error with cassandra bean inition");
	}

	@Override
	public void destroy() throws Exception {
		
		LOG.debug("Cleaning thread pool of Hector connections");
		Collection<HClientPool> pList =
				this.constants.getCurrentClstr().getConnectionManager().getActivePools();
		Iterator<HClientPool> iter = pList.iterator();
		while(iter.hasNext())
			iter.next().shutdown();
		
		for(String host: this.constants.HOST_DEFS)
			this.constants.getCurrentClstr().getConnectionManager().removeCassandraHost(new CassandraHost(host));
		LOG.debug("Removing all hosts from current session...");
		
		this.constants.getCurrentClstr().getConnectionManager().shutdown();
		HFactory.shutdownCluster(this.constants.getCurrentClstr());
		
		this.constants = null;
		LOG.debug("Forsed call of GC and finalization ...");
		System.runFinalization();
		System.gc();
		
	}
}
