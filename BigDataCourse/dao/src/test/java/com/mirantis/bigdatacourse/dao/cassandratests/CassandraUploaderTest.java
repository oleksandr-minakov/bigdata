package com.mirantis.bigdatacourse.dao.cassandratests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.mirantis.bigdatacourse.dao.BookUploader;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;

public class CassandraUploaderTest {
	@Ignore
	@Test
	public void uploaderTest() throws DaoException, IOException {
		

		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", hosts);
		DaoCassandra dao = new DaoCassandra(cts);
		
		BookUploader uploader = new BookUploader(dao, "booksToBeUploaded/");
		int a = uploader.bookUploder();
		Assert.assertTrue (a != 0);
	}
}
