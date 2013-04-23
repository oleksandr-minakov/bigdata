package com.mirantis.bigdatacourse.mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;


public class WordCounterJob {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{

		private Text words = new Text();
		
		@Override
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)throws IOException {
			
			String wordLine = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(wordLine, " .-!?=,/\''");
			while(tokenizer.hasMoreElements()){
				words.set(tokenizer.nextToken());
				output.collect(words, new IntWritable(1));
			}
			close();
		}
		
	}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{
			
		@Override
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

			int summary = 0;
			while(values.hasNext()){
				summary +=	values.next().get();
			}
			output.collect(key, new IntWritable(summary));
			close();
		}
		
	}

}
