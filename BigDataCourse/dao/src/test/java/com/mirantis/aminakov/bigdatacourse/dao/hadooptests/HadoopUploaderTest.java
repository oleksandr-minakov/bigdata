package com.mirantis.aminakov.bigdatacourse.dao.hadooptests;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import com.mirantis.aminakov.bigdatacourse.dao.BookUploader;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.DaoHDFS;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetLastIndexJob;

public class HadoopUploaderTest {
	@Ignore
	@Test
	public void uploaderTest() throws DaoException, IOException{
		

		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		DaoHDFS dao = new DaoHDFS(newOne);
		
		BookUploader uploader = new BookUploader(dao, "booksToBeUploaded/", new GetLastIndexJob(newOne).getIncrementedNewID());
		int res = uploader.bookUploder();
		Assert.notNull(res);
	}
}