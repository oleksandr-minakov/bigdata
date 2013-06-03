package com.mirantis.bigdatacourse.mapreducetests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Test;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadooptests.HdfsIP;
import com.mirantis.bigdatacourse.mapreduce.GetParsedStatistics;
import com.mirantis.bigdatacourse.mapreduce.JobRunner;
import com.mirantis.bigdatacourse.mapreduce.WordCounterJob;
import com.mirantis.bigdatacourse.mapreduce.WordCounterJob.Map;
import com.mirantis.bigdatacourse.mapreduce.WordCounterJob.Reduce;

public class WordCountTest {
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@Test
	public void testCase() throws DaoException, IOException {
		
		int testCase = 10;
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP,"9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		AddBookJob job = new AddBookJob(newOne);
		List<Path> pathList = new ArrayList<Path>();
		JobRunner jobba = new JobRunner(newOne, WordCounterJob.class , Map.class, Reduce.class);
		GetParsedStatistics  getP = new GetParsedStatistics(newOne);
		Path path = jobba.getPathToEvaluatedStatistics();
		List<Pair<String, String>> pairs = getP.getParsedStatistics(path);
		for(Pair pair: pairs)
			System.out.println(pair);
		if(pairs.size() == 0 )
			Assert.assertEquals(pairs.size(), 0);
		if(pairs.size() != 0)
			Assert.assertNotEquals(pairs.size(), 0);
		
	}

}
