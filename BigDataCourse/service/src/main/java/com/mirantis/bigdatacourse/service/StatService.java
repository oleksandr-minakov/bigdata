package com.mirantis.bigdatacourse.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.mirantis.bigdatacourse.dao.Dao;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.bigdatacourse.mapreduce.GetParsedStatistics;
import com.mirantis.bigdatacourse.mapreduce.JobRunner;
import com.mirantis.bigdatacourse.mapreduce.MapReduceThread;

public class StatService {
	
	@Autowired(required = false)
	private Object job;
	@Autowired(required = false)
	private Object mapper;
	@Autowired(required = false)
	private Object reducer;
	@Autowired(required = false)
	private HadoopConnector configuration;
    @Autowired(required = false)
    private Dao dao;
	
	private MapReduceThread mapRedThread;
	private ThreadPoolTaskExecutor pool;
	private TaskExecutor exec;
	
	public Object getJob() {
		return job;
	}
	
	public void setJob(Object job) {
		this.job = job;
	}
	
	public Object getMapper() {
		return mapper;
	}
	
	public void setMapper(Object mapper) {
		this.mapper = mapper;
	}
	
	public Object getReducer() {
		return reducer;
	}
	
	public void setReducer(Object reducer) {
		this.reducer = reducer;
	}
	
	public HadoopConnector getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(HadoopConnector configuration) {
		this.configuration = configuration;
	}
	
	public ThreadPoolTaskExecutor getPool() {
		return pool;
	}
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	public void setUpService() {
		
		mapRedThread = new MapReduceThread();
			mapRedThread.setConfiguration(this.configuration);
			mapRedThread.setJobClass(this.job);
			mapRedThread.setMapperClass(this.mapper);
			mapRedThread.setReducerClass(this.reducer);
		
		pool = new ThreadPoolTaskExecutor();
			pool.setCorePoolSize(1);
			pool.setMaxPoolSize(1);
			pool.createThread(mapRedThread);
			pool.setQueueCapacity(1);
			pool.setWaitForTasksToCompleteOnShutdown(true);
			pool.initialize();
		
		exec = pool;
	}
	@SuppressWarnings("rawtypes")
	public List<Pair<String, Double>> viewStatistics() throws IOException, DaoException {
		
		List<Pair<String, Double>> pairs = new ArrayList<Pair<String, Double>>();
		if(this.configuration.getFS().exists(new Path("/Statistics")) && this.configuration.getFS().listStatus(new Path("/Statistics")).length > 1) {
			
			GetParsedStatistics  parser = new GetParsedStatistics(this.configuration);
			JobRunner jobRunner = new JobRunner(this.configuration, job.getClass(), ((Mapper)mapper).getClass(), ((Reducer)reducer).getClass());
			Path path = jobRunner.getPathToEvaluatedStatistics();
			pairs = parser.getParsedStatistics(path);
			
			return pairs;
		}
		else
			return pairs;
		
	}
	
	public List<Pair<String, Double>> recalculateStatistics() throws IOException, DaoException {
		
		List<Pair<String, Double>> pairs = new ArrayList<Pair<String, Double>>();
		
		if(this.configuration.getFS().exists(new Path("/Statistics")))
			this.configuration.getFS().delete(new Path("/Statistics"), true);
		
		setUpService();
		exec.execute(this.mapRedThread);
		
		return pairs;
	}

}
