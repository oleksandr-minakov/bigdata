package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.BookUploader;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

public class CassandraUploaderTest {
	
	@Ignore
	@Test
	public void uploaderTest() throws DaoException, IOException{
		

		Constants cts = new Constants("Test Cluster", "Bookshelf", "Books", CassandraIP.IP);
		DaoCassandra dao = new DaoCassandra(cts);
		
		BookUploader uploader = new BookUploader(dao, "booksToBeUploaded/");
		uploader.bookUploder();
	}
}
