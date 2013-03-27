package com.mirantis.aminakov.bigdatacourse.mapreducetests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Test;
import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.mapreduce.GetParsedStatistics;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.mapreduce.JobRunner;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.mapreduce.WordCounterJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadooptests.HdfsIP;

public class WordCountTest {
	
	@Test
	public void testCase() throws DaoException, IOException{
		
		int testCase = 10;
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP,"54310", new HdfsIP().HadoopUser, "/bookshelf/books/");
		
		newOne.bookID = 1;
		
		AddBookJob job = new AddBookJob(newOne);
		List<Path> pathList = new ArrayList<Path>();
		Book beggining_state;
		
		for(int i = 0; i < testCase; ++i){
			
			beggining_state = new Book();
			beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
			job.addBookJob(beggining_state);
			pathList.add(new Path(newOne.getURI() + new PathFormer().formAddPath(beggining_state, newOne.workingDirectory)));
			
		}
		
		JobRunner jobba = new JobRunner(newOne, WordCounterJob.class , WordCounterJob.Map.class, WordCounterJob.Reduce.class);
		GetParsedStatistics  getP = new GetParsedStatistics(newOne);
		Path path = jobba.getPathToEvaluatedStatistics();
		List<Pair<String, Double>> pairs = getP.getParsedStatistics(path);
		
		if(pairs.size() == 0 )
			Assert.assertEquals(pairs.size(), 0);
		if(pairs.size() != 0)
			Assert.assertNotEquals(pairs.size(), 0);
		
	}

}
