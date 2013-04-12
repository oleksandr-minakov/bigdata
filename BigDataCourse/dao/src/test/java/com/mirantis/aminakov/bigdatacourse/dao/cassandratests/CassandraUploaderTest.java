package com.mirantis.aminakov.bigdatacourse.dao.cassandratests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.BookUploader;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.aminakov.bigdatacourse.dao.cassandra.DaoCassandra;

public class CassandraUploaderTest {
	
	@Test
	public void uploaderTest() throws DaoException, IOException{
		

		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		hosts.add(CassandraIP.IP2);
		hosts.add(CassandraIP.IP3);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		
		cts.bookID = dao.getMaxIndex()+1;
		
		BookUploader uploader = new BookUploader(dao, "booksToBeUploaded/", cts.bookID);
		int a = uploader.bookUploder();
		Assert.assertTrue (a != 0);
	}
}
