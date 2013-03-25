package com.mirantis.aminakov.bigdatacourse.mapreducetests;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.PathFormer;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.job.AddBookJob;
import com.mirantis.aminakov.bigdatacourse.dao.hadooptests.HdfsIP;
import com.mirantis.aminakov.bigdatacourse.mapreduce.GetWordsFrequenciesJob;
import com.mirantis.aminakov.bigdatacourse.mapreduce.GetWordsFrequenciesJob.Map;
import com.mirantis.aminakov.bigdatacourse.mapreduce.GetWordsFrequenciesJob.Reduce;

public class WordCountTest {
	
	
	@Test
	public void testCase() throws DaoException, IOException{
		
		HadoopConnector newOne = new HadoopConnector(new HdfsIP().HadoopIP,"54310", new HdfsIP().HadoopUser, "/bookshelf/books/");
		newOne.bookID = 100;
		Book beggining_state = new Book();
		beggining_state.newBook("CassandraTest", "Test", "Tester", new FileInputStream("src/main/resources/testbook"));
		new AddBookJob(newOne).addBookJob(beggining_state);
		Path bookPath = new Path(new PathFormer().formAddPath(beggining_state, newOne.workingDirectory));
		
		JobConf conf = new JobConf(GetWordsFrequenciesJob.class);
		conf.setJobName("wordcount");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(conf, new Path(newOne.getURI() + new PathFormer().formAddPath(beggining_state, "/bookshelf/books/")));
		FileOutputFormat.setOutputPath(conf, new Path(newOne.getURI() + "/bookshelf/MapReduce"));
		
		JobClient.runJob(conf);
	}

}
