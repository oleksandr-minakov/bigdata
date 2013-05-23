package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.BookUploader;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.DaoHDFS;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import org.junit.Test;
import org.springframework.util.Assert;

public class HadoopUploaderTest {

	@Test
	public void uploaderTest() throws DaoException {

		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books_dev/", 1);
		DaoHDFS dao = new DaoHDFS(newOne);
		BookUploader uploader = new BookUploader(dao, "booksToBeUploaded/");
		int res = uploader.bookUploader();
		Assert.notNull(res);
	}
}
