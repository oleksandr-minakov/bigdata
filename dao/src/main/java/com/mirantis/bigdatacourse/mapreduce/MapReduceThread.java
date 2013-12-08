package com.mirantis.bigdatacourse.mapreduce;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.Pair;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes"})

public class MapReduceThread implements Runnable {

	public static final Logger LOG = Logger.getLogger(MapReduceThread.class);
	
	private Object job;
	private Object mapper;
	private Object reducer;
	private HadoopConnector configuration;
	private Path path;
	private List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
	
	public void setJobClass(Object job){
		
		this.job = job;
	}
	
	public void setMapperClass(Object mapper){
		
		this.mapper = mapper;
	}
	
	public void setReducerClass(Object reducer){
		
		this.reducer = reducer;
	}
	
	public void setConfiguration(HadoopConnector configuration){
		
		this.configuration = configuration;
	}
	
	public List<Pair<String, String>> getPairs(){
		
		return this.pairs;
	}
	
	public Path getPath(){
		
		return this.path;
	}
	
	@Override
	public void run(){
		
		try {
			
			GetParsedStatistics  getPath = new GetParsedStatistics(this.configuration);
			JobRunner jobRunner = new JobRunner(this.configuration, this.job.getClass(), ((Mapper)this.mapper).getClass(), ((Reducer)this.reducer).getClass());
			this.path = jobRunner.getPathToEvaluatedStatistics();
			this.pairs = getPath.getParsedStatistics(this.path);			
		} catch (DaoException | IOException e) {
			LOG.info("Exception in MapReduce job, Message:" + e.getMessage());
			LOG.debug("Exception in MapReduce job, Message:" + e.getMessage());
		}
	}

}
