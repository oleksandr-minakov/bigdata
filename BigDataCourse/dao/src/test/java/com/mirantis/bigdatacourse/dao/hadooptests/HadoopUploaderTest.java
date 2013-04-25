package com.mirantis.bigdatacourse.dao.hadooptests;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import com.mirantis.bigdatacourse.dao.BookUploader;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.DaoHDFS;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;

public class HadoopUploaderTest {
	@Ignore
	@Test
	public void uploaderTest() throws DaoException, IOException {
		

		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		DaoHDFS dao = new DaoHDFS(newOne);
		
		BookUploader uploader = new BookUploader(dao, "booksToBeUploaded/");
		int res = uploader.bookUploder();
		Assert.notNull(res);
	}
}
