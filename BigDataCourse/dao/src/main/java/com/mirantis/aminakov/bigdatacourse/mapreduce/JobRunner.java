package com.mirantis.aminakov.bigdatacourse.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.log4j.Logger;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.PathFormer;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetAllBooksJob;
import com.mirantis.bigdatacourse.dao.hadoop.job.GetBooksCountJob;
@SuppressWarnings({"rawtypes", "unused"})
public class JobRunner {
	
	public static final Logger LOG = Logger.getLogger(JobRunner.class);
	private Class jobClass;
	private Class<? extends Mapper> mapper;
	private Class<? extends Reducer> reducer;
	private final String jobName = "defualt";
	private HadoopConnector hadoop;
	
	public JobRunner(HadoopConnector hadoop, Class job, Class<? extends Mapper> map, Class<? extends Reducer> reduce){
		
		this.jobClass = job;
		this.mapper = map;
		this.reducer = reduce;
		this.hadoop = hadoop;
		
	}

	public Path getPathToEvaluatedStatistics() throws DaoException, IOException{
		
		Path statPath = new Path(this.hadoop.getURI() + "/Statistics");
		LOG.debug("Checking statistics file on existance ...");
		if(this.hadoop.getFS().exists(new Path("/Statistics")) && this.hadoop.getFS().listStatus(new Path("/Statistics")).length > 1)
			return statPath;

		GetAllBooksJob get = new GetAllBooksJob(this.hadoop);
		List<Path> pathList = new ArrayList<Path>();
		
		int count = new GetBooksCountJob(this.hadoop).getBooksCount();
		LOG.debug("Getting books count ...");
		if(count == 0)
			return new Path(this.hadoop.workingDirectory);
		
		List<Book> bookList = get.getAllBooksJob(1, count);
		LOG.debug("Collecting books by threir count ...");

		for(Book book: bookList)
			pathList.add(new Path(this.hadoop.getURI() + new PathFormer().formAddPath(book, this.hadoop.workingDirectory)));
		
		Path[] paths = new Path[pathList.size()];
		pathList.toArray(paths);
		LOG.debug("Coverting List<Path> pathList to Path[] ...");
		
		JobConf conf = new JobConf(this.jobClass);
		LOG.debug("Setting Job class");
			conf.setJobName(this.jobName);
			LOG.debug("Setting Job name");
			conf.setOutputKeyClass(Text.class);
			LOG.debug("Setting Job Output Key class");
			conf.setOutputValueClass(IntWritable.class);
			LOG.debug("Setting Job Output Value class");
			conf.setMapperClass(this.mapper);
			LOG.debug("Setting Job Mapper class");
			conf.setCombinerClass(this.reducer);
			LOG.debug("Setting Job Combiner class");
			conf.setReducerClass(this.reducer);
			LOG.debug("Setting Job Reducer class");
			conf.setInputFormat(TextInputFormat.class);
			LOG.debug("Setting Job Input Format class");
			conf.setOutputFormat(TextOutputFormat.class);
			LOG.debug("Setting Job Output Format class");
			
		FileInputFormat.setInputPaths(conf, paths);
		LOG.debug("Setting input paths");
		FileOutputFormat.setOutputPath(conf, statPath);
		LOG.debug("Setting output path");
		JobClient.runJob(conf);
		LOG.debug("Executing job ...");
		LOG.info("Executing job ...");
		return statPath;
	}
}
