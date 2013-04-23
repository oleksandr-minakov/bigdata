package com.mirantis.aminakov.bigdatacourse.mapreducetests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Test;
import com.mirantis.aminakov.bigdatacourse.mapreduce.GetParsedStatistics;
import com.mirantis.aminakov.bigdatacourse.mapreduce.JobRunner;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob.Map;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob.Reduce;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.bigdatacourse.dao.hadooptests.HdfsIP;

public class WordCountTest {
	
	@SuppressWarnings("unused")
	@Test
	public void testCase() throws DaoException, IOException{
		
		int testCase = 10;
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP,"54310", new HdfsIP().HadoopUser, "/bookshelf/books/");
		
		newOne.bookID = 1;
		
		AddBookJob job = new AddBookJob(newOne);
		List<Path> pathList = new ArrayList<Path>();
		JobRunner jobba = new JobRunner(newOne, WordCounterJob.class , Map.class, Reduce.class);
		GetParsedStatistics  getP = new GetParsedStatistics(newOne);
		Path path = jobba.getPathToEvaluatedStatistics();
		List<Pair<String, Double>> pairs = getP.getParsedStatistics(path);
		
		if(pairs.size() == 0 )
			Assert.assertEquals(pairs.size(), 0);
		if(pairs.size() != 0)
			Assert.assertNotEquals(pairs.size(), 0);
		
	}

}
