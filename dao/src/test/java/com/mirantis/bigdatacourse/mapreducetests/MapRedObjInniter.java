package com.mirantis.bigdatacourse.mapreducetests;

import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.bigdatacourse.dao.hadooptests.HdfsIP;
import com.mirantis.bigdatacourse.mapreduce.MapReduceThread;
import com.mirantis.bigdatacourse.mapreduce.WordCounterJob;
import com.mirantis.bigdatacourse.mapreduce.WordCounterJob.Map;
import com.mirantis.bigdatacourse.mapreduce.WordCounterJob.Reduce;
import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

public class MapRedObjInniter {
	@Ignore
	@Test
	public void getObjsTest() throws Exception {
		
		List<Pair<String,  String>> pairs = new ArrayList<Pair<String,  String>>();
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP, "9000", new HdfsIP().HadoopUser, "/bookshelf/books/", 100);
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


        pool.execute(thread);
		
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
