package com.mirantis.bigdatacourse.dao.hadooptests;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBooksCountJob;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class GetBookCountJobTest {

	@Test
	public void testCase() throws DaoException, IOException {
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		GetBooksCountJob count = new GetBooksCountJob(newOne);
		System.out.println(Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory))).size());
		System.out.println(count.getBooksCount());
		System.out.println("GetBookCountJobTest " + (count.getBooksCount() == Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory))).size()));
		assertEquals(count.getBooksCount(), Arrays.asList(newOne.getFS().listStatus(new Path(newOne.workingDirectory))).size());
	}
}
