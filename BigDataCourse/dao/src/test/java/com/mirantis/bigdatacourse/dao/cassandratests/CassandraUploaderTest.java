package com.mirantis.bigdatacourse.dao.cassandratests;

import com.mirantis.bigdatacourse.dao.BookUploader;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.cassandra.Constants;
import com.mirantis.bigdatacourse.dao.cassandra.DaoCassandra;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CassandraUploaderTest {
	@Ignore
	@Test
	public void uploaderTest() throws DaoException, IOException {
		

		List<String> hosts = new ArrayList<String>();
		hosts.add(CassandraIP.IP1);
		
		Constants cts = new Constants("Cassandra Cluster", "Bookshelf", "Books", CassandraIP.IP1);
		DaoCassandra dao = new DaoCassandra(cts);
		
		BookUploader uploader = new BookUploader(dao, "booksToBeUploaded/");
		int a = uploader.bookUploder();
		Assert.assertTrue (a != 0);
	}
}
