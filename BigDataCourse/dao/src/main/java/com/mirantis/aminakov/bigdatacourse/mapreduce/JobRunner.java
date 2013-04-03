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

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetAllBooksJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.GetBooksCountJob;
@SuppressWarnings({"rawtypes", "unused"})
public class JobRunner {
	
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
		if(this.hadoop.getFS().exists(new Path("/Statistics")) && this.hadoop.getFS().listStatus(new Path("/Statistics")).length > 1)
			return statPath;

		GetAllBooksJob get = new GetAllBooksJob(this.hadoop);
		List<Path> pathList = new ArrayList<Path>();
		
		int count = new GetBooksCountJob(this.hadoop).getBooksCount();
		
		if(count == 0)
			return new Path(this.hadoop.workingDirectory);
		
		List<Book> bookList = get.getAllBooksJob(1, count);

				for(Book book: bookList){
			pathList.add(new Path(this.hadoop.getURI() + new PathFormer().formAddPath(book, this.hadoop.workingDirectory)));
		}
		Path[] paths = new Path[pathList.size()];
		pathList.toArray(paths);
		
		JobConf conf = new JobConf(this.jobClass);
			conf.setJobName(this.jobName);
			conf.setOutputKeyClass(Text.class);
			conf.setOutputValueClass(IntWritable.class);
			conf.setMapperClass(this.mapper);
			conf.setCombinerClass(this.reducer);
			conf.setReducerClass(this.reducer);
			conf.setInputFormat(TextInputFormat.class);
			conf.setOutputFormat(TextOutputFormat.class);
			
		FileInputFormat.setInputPaths(conf, paths);
		FileOutputFormat.setOutputPath(conf, statPath);
		JobClient.runJob(conf);
		
		return statPath;		
	}
}
