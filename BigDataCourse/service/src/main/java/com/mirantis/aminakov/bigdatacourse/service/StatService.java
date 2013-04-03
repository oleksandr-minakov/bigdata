package com.mirantis.aminakov.bigdatacourse.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.springframework.beans.factory.annotation.Autowired;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.Pair;
import com.mirantis.aminakov.bigdatacourse.mapreduce.GetParsedStatistics;
import com.mirantis.aminakov.bigdatacourse.mapreduce.JobRunner;

public class StatService {
	
	@Autowired
	private Object job;
	@Autowired
	private Object mapper;
	@Autowired
	private Object reducer;
	@Autowired
	private HadoopConnector configuration;
	
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
	
	public List<Pair<String, Double>> viewStatistics(Path path) throws IOException, DaoException{
		
		List<Pair<String, Double>> pairs = new ArrayList<Pair<String, Double>>();
		if(this.configuration.getFS().exists(path)){
			
			GetParsedStatistics  parser = new GetParsedStatistics(this.configuration);
			pairs = parser.getParsedStatistics(path);
			
			return pairs;
		}
		else
			return pairs;
		
	}
	
	@SuppressWarnings("rawtypes")
	public List<Pair<String, Double>> recalculatetatistics() throws IOException, DaoException{
		
		List<Pair<String, Double>> pairs = new ArrayList<Pair<String, Double>>();
		if(this.configuration.getFS().exists(new Path("/Statistics")))
			this.configuration.getFS().delete(new Path("/Statistics"), true);
		JobRunner jobRunner = new JobRunner(this.configuration, job.getClass(), ((Mapper)mapper).getClass(), ((Reducer)reducer).getClass());
		GetParsedStatistics  getP = new GetParsedStatistics(this.configuration);
		Path path = jobRunner.getPathToEvaluatedStatistics();
		pairs = getP.getParsedStatistics(path);
		
		return pairs;
	}
}
