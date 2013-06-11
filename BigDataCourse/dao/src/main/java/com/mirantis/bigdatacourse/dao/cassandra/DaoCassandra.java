package com.mirantis.bigdatacourse.dao.cassandra;

import com.mirantis.bigdatacourse.dao.*;
import me.prettyprint.cassandra.connection.HClientPool;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DaoCassandra implements Dao {

	Constants constants;
	public static final Logger LOG = Logger.getLogger(DaoCassandra.class);
	
	public DaoCassandra(Constants constants) {
		super();
		this.constants = constants;
	}

    @Override
	public void afterPropertiesSet() throws DaoException {
		LOG.debug("Checking DaoCassandra bean");
		if(this.constants == null)
			throw new DaoException("Error with cassandra bean initialization.");
	}

	@Override
	public void destroy() throws Exception {
		LOG.debug("Cleaning thread pool of Hector connections");
		Collection<HClientPool> pList =
				this.constants.getCurrentClstr().getConnectionManager().getActivePools();
        for (HClientPool aPList : pList) {
            aPList.shutdown();
        }

		this.constants.getCurrentClstr().getConnectionManager().removeCassandraHost(new CassandraHost(this.constants.HOST));
		LOG.debug("Removing all hosts from current session...");

		this.constants.getCurrentClstr().getConnectionManager().shutdown();
		HFactory.shutdownCluster(this.constants.getCurrentClstr());
		
		this.constants = null;
		LOG.debug("Forced call of GC and finalization ...");
		System.runFinalization();
		System.gc();
	}

	@Override
	public int addBook(Book book) throws DaoException {
		
		List<String> mods = new ArrayList<>();
		KeyGenerator idGen = new KeyGenerator();
		
		mods.add(String.valueOf(Thread.activeCount()));
		mods.add(Thread.currentThread().getName());
		mods.add(Thread.currentThread().toString());
		mods.add(Thread.currentThread().getState().toString());
		mods.add(String.valueOf(new Date().getTime()));
		String newID = idGen.getNewID(mods);
		book.setId(newID);
		
		try {
			Mutator<String> mutator = HFactory.createMutator(constants.getKeyspace(), StringSerializer.get());
			LOG.debug("Creating new mutator");
			
			for(HColumn<String, String> col: BookConverter.getInstance().book2row(book))
				mutator.insert(newID, constants.CF_NAME, col);
			
			LOG.debug("Performing book insertion...");
			HColumn<String,String> updater = HFactory.createColumn(newID, newID);
				mutator.insert(book.getTitle(), "titles", updater);
				mutator.insert(book.getAuthor(), "authors", updater);
				mutator.insert(book.getGenre(), "genres", updater);
				mutator.insert(book.getReadableText(), "texts", updater);
			
		} catch (Exception e) {
			LOG.debug(e.getMessage());
            throw new DaoException(e);
        }
		LOG.info("Book was added, id:" + book.getId());
		return 0;
	}

	@Override
	public int delBook(String id) throws DaoException {
		
		Mutator<String> mutator = HFactory.createMutator(constants.getKeyspace(), StringSerializer.get());
//		String newID = getHash(id);
		LOG.debug("Creating new mutator");
		
		try {
				MultigetSliceQuery<String, String, String> book =
						HFactory.createMultigetSliceQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
				
				book.setColumnFamily(constants.CF_NAME);
				book.setKeys(id);
				book.setRange("", "", false, Integer.MAX_VALUE - 1);
				book.setRange("", "", false, Integer.MAX_VALUE - 1);

				LOG.debug("Forming new MultigetSliceQuery<String, String, String>");
				
				QueryResult<Rows<String, String, String>> result = book.execute();
				
				LOG.debug("Executing RangeSlicesQuery<String, String, String>");
				
				Rows<String, String, String> orderedRows = result.get();
				
				Book bookForDelete = BookConverter.getInstance().row2book(orderedRows.getByKey(id).getColumnSlice().getColumns());
				
				mutator.delete(bookForDelete.getTitle(), "titles", id, StringSerializer.get());
				mutator.delete(bookForDelete.getAuthor(), "authors", id, StringSerializer.get());
				mutator.delete(bookForDelete.getGenre(), "genres", id, StringSerializer.get());
				mutator.delete(bookForDelete.getReadableText(), "texts", id, StringSerializer.get());
				mutator.delete(getHash(id), constants.CF_NAME, null, StringSerializer.get());
				LOG.debug("Book was deleted, id:" + id);
		} catch(Exception e){
            LOG.info("Error happened", e);
			throw new DaoException(e);
		}
		LOG.info("Book was deleted, id:" + id);
		return 0;
	}

	@Override
	public PaginationModel getAllBooks(int pageNum, int pageSize) throws DaoException {
		
		List<String> keyStorage = getAllRowKeys();
		List<Book> ret = new ArrayList<Book>();
        PaginationModel model = new PaginationModel();
		List<String> neededKeys = new ArrayList<String>();

		if(pageNum < 0 || pageNum > getPageCount(keyStorage.size(), pageSize)) {
            model.setBooks(ret);
			return model;
		} else {
			if(pageNum * pageSize > keyStorage.size()) {
				for(Book book:getBooks(keyStorage)) {
					if(!book.equals(null) && book.getId().length() != 0) {          //FIX WTF?
						ret.add(book);
					}
				}
				LOG.info("Getting all books with pagination");
				LOG.debug("Getting all books with pagination");
                model.setBooks(ret);
				return model;
			} else {
				neededKeys = keyStorage.subList((pageNum - 1) * pageSize, pageNum * pageSize);
				for(Book book:getBooks(neededKeys)) {
					if(book != null && book.getId() != null && book.getAuthor() != null && book.getGenre() != null && book.getTitle() != null) {
						ret.add(book);
					}
				}
				LOG.info("Getting all books with pagination");
				LOG.debug("Getting all books with pagination");
                model.setBooks(ret);
				return model;
			}
		}
	}

	public List<Book> getBooks(List<String> rowKeys) throws DaoException {

		List<Book> booksByKeys = new ArrayList<Book>();

		MultigetSliceQuery<String, String, String> books =HFactory.createMultigetSliceQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		books.setColumnFamily(constants.CF_NAME);
		books.setKeys(rowKeys);
		books.setRange("", "", false, Integer.MAX_VALUE-1);
		LOG.debug("Forming new MultigetSliceQuery<String, String, String>");
		try {
				QueryResult<Rows<String, String, String>> result = books.execute();
				LOG.debug("Executing RangeSlicesQuery<String, String, String>");
				Rows<String, String, String> orderedRows = result.get();
				for(Row<String, String, String> row:orderedRows)
					booksByKeys.add(BookConverter.getInstance().row2book(row.getColumnSlice().getColumns()));
				
				LOG.debug("Collection books...");
				return booksByKeys;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public List<String> getAllRowKeys() throws DaoException {

		List<String> pagedBooks = new ArrayList<String>();

		RangeSlicesQuery<String, String, String> books = HFactory.createRangeSlicesQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		books.setColumnFamily(constants.CF_NAME);
		books.setKeys("", "");
		books.setReturnKeysOnly();
		books.setRowCount(Integer.MAX_VALUE);
		LOG.debug("Forming new RangeSlicesQuery<String, String, String>");
		try {
			QueryResult<OrderedRows<String, String, String>> result = books.execute();
			LOG.debug("Executing RangeSlicesQuery<String, String, String>");
			OrderedRows<String, String, String> orderedRows = result.get();
        	List<Row<String, String, String>> keys = orderedRows.getList();
        	for(Row<String, String, String> row: keys){
        		pagedBooks.add(row.getKey());
        	}
        	LOG.debug("Collection row keys...");
		} catch (Exception e) {
			throw new DaoException(e);
		}
        LOG.info("Getting all row keys");
		return pagedBooks;
	}
	
	public int getPageCount(int amountOfRecords, int pageSize){
		int pages = amountOfRecords / pageSize;
		if(amountOfRecords % pageSize != 0)
			return pages + 1;
		else
			return pages;
	}


    //FIX
	@Override
	public PaginationModel getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
		
        try {
    		List<Book> booksBy= new ArrayList<Book>();
            PaginationModel model = new PaginationModel();
			booksBy = getBooksByToken(pageNum, pageSize, "titles", title);
	        model.setBooks(booksBy);
			return model;
		} catch (IOException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public PaginationModel getBookByText(int pageNum, int pageSize, String text) throws DaoException {
		try {
			List<Book> booksBy= new ArrayList<Book>();
            PaginationModel model = new PaginationModel();
			booksBy = getBooksByToken(pageNum, pageSize, "texts", text);
			model.setBooks(booksBy);
            return model;
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public PaginationModel getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
		try {
			List<Book> booksBy= new ArrayList<Book>();
            PaginationModel model = new PaginationModel();
			booksBy = getBooksByToken(pageNum, pageSize, "authors", author);
            model.setBooks(booksBy);
			return model;
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public PaginationModel getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
		try {
			List<Book> booksBy= new ArrayList<Book>();
            PaginationModel model = new PaginationModel();
			booksBy = getBooksByToken(pageNum, pageSize, "genres", genre);
            model.setBooks(booksBy);
			return model;
		} catch (IOException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void closeConnection() throws DaoException {
		
		Collection<HClientPool> pList =
				this.constants.getCurrentClstr().getConnectionManager().getActivePools();
        for (HClientPool aPList : pList) {
            aPList.shutdown();
        }
		
		this.constants.getCurrentClstr().getConnectionManager().removeCassandraHost(new CassandraHost(this.constants.HOST));
		LOG.debug("Removing all hosts from current session...");
		
		this.constants.getCurrentClstr().getConnectionManager().shutdown();
		HFactory.shutdownCluster(this.constants.getCurrentClstr());

	}


	public int getNumberOfRecordsBy(String whereToSeek, String whatToSeekFor) throws DaoException {
		
		MultigetSliceQuery<String, String, String> book =
				HFactory.createMultigetSliceQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		book.setColumnFamily(whereToSeek);
		book.setKeys(whatToSeekFor);
		book.setRange("", "", false, Integer.MAX_VALUE - 1);
		LOG.debug("Forming new MultigetSliceQuery<String, String, String>");
		QueryResult<Rows<String, String, String>> result = book.execute();
		LOG.debug("Executing RangeSlicesQuery<String, String, String>");
		Rows<String, String, String> orderedRows = result.get();
		
		return orderedRows.getByKey(whatToSeekFor).getColumnSlice().getColumns().size();
	}
	
	public String getHash(String id) throws DaoException {
		
		String hash = "";
		MessageDigest hashAlg;
		
		try {
			
			hashAlg = MessageDigest.getInstance("MD5");
			hashAlg.reset();
			hashAlg.update(id.getBytes());
			byte[] byteHash = hashAlg.digest();
            for (byte aByteHash : byteHash) {
                hash += Integer.toString((aByteHash & 0xff) + 0x100, 16).substring(1);
            }
		} catch (NoSuchAlgorithmException e) {
			throw new DaoException(e);
		}
		LOG.debug("Calculation new hash by id MD5(id)");
		return hash;
	}

	public List<Book> getBooksByToken(int pageNum, int pageSize, String cfToSearch, String tokenKey) throws DaoException, IOException {
		
		List<Book> booksBy= new ArrayList<Book>();
		List<String> keysForSlice= new ArrayList<String>();
		List<String> keysForSliceCut= new ArrayList<String>();
		
		MultigetSliceQuery<String, String, String> book =
				HFactory.createMultigetSliceQuery(constants.getKeyspace(), StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
		
		book.setColumnFamily(cfToSearch);
		book.setKeys(tokenKey);
		book.setRange("", "", false, Integer.MAX_VALUE-1);
		
		LOG.debug("Forming new MultigetSliceQuery<String, String, String>");
		
		QueryResult<Rows<String, String, String>> result = book.execute();
		
		LOG.debug("Executing RangeSlicesQuery<String, String, String>");
		
		Rows<String, String, String> orderedRows = result.get();
		for(HColumn<String, String> col:orderedRows.getByKey(tokenKey).getColumnSlice().getColumns()) 
			keysForSlice.add(col.getValue());
		
		if(keysForSlice.size() > pageSize*pageNum)
            keysForSliceCut =  keysForSlice.subList((pageNum-1)*pageSize, pageNum*pageSize);

        if(keysForSlice.size() > pageSize*(pageNum-1) && pageNum*pageSize >= keysForSlice.size())
            keysForSliceCut = keysForSlice.subList(pageSize*(pageNum-1), keysForSlice.size());

        if(keysForSlice.size() < pageSize && keysForSlice.size() <= pageNum*pageSize)
            keysForSliceCut = keysForSlice.subList(0, keysForSlice.size());
		
		if(keysForSlice.size() ==0)
			keysForSliceCut = keysForSlice;
		
		book.setColumnFamily(constants.CF_NAME);
		book.setKeys(keysForSliceCut);
		book.setRange("", "", false, 5);
		LOG.debug("Forming new MultigetSliceQuery<String, String, String>");
		result = book.execute();
		LOG.debug("Executing RangeSlicesQuery<String, String, String>");
		orderedRows = result.get();
		
		for(String bookKey: keysForSliceCut)
			booksBy.add(BookConverter.getInstance().row2book(orderedRows.getByKey(bookKey).getColumnSlice().getColumns()));
		
		return booksBy;
	}
}
