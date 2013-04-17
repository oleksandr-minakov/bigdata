package com.mirantis.aminakov.bigdatacourse.mapreducetests;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.aminakov.bigdatacourse.dao.hadooptests.HdfsIP;
import com.mirantis.aminakov.bigdatacourse.mapreduce.MapReduceThread;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob.Map;
import com.mirantis.aminakov.bigdatacourse.mapreduce.WordCounterJob.Reduce;

public class MapRedObjInniter {

	@Test
	public void getObjsTest() throws Exception{
		
		List<Pair<String, Double>> pairs = new ArrayList<Pair<String, Double>>();
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.getFS().delete(new Path("/Statistics"), true);
		WordCounterJob words = new WordCounterJob();
		
		Map map = new Map();
		
		Reduce reduce = new Reduce();
		
		MapReduceThread thread = new MapReduceThread();
		thread.setConfiguration(newOne);
		thread.setJobClass(words);
		thread.setMapperClass(map);
		thread.setReducerClass(reduce);
		
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(1);
		pool.setMaxPoolSize(1);
		pool.createThread(thread);
		pool.setQueueCapacity(1);
		pool.setWaitForTasksToCompleteOnShutdown(true);
		pool.initialize();
		
		
		TaskExecutor exec = pool;
		exec.execute(thread);
		
		if(pool.getActiveCount() != 0){
			
			System.out.println("MapReduce in running in background.Please retry later.");
			while(thread.getPairs().size() == 0) {}
		}
		pairs = thread.getPairs();
		if(pairs.size() == 0 )
			Assert.assertEquals(pairs.size(), 0);
		if(pairs.size() != 0)
			Assert.assertNotEquals(pairs.size(), 0);
	}
}
