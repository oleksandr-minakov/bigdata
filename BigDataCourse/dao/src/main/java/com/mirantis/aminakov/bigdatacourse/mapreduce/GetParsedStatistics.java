package com.mirantis.aminakov.bigdatacourse.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration.Pair;

public class GetParsedStatistics {
	
	private HadoopConnector hadoop;
	
	public GetParsedStatistics(HadoopConnector hadoop){
		
		this.hadoop = hadoop;
	}
	
	public List<Pair<String, Double>> getParsedStatistics(Path statPath) throws IOException, DaoException{
		
		List<Pair<String, Double>> retFrequency = new ArrayList<Pair<String, Double>>();
		
		if(statPath.equals(new Path(this.hadoop.workingDirectory))){
			return retFrequency;
		}
		List<FileStatus> fsList = Arrays.asList(hadoop.getFS().listStatus(statPath));
		
		if(fsList.size() >= 2){
			
			FSDataInputStream in = hadoop.getFS().open(fsList.get(1).getPath());
			
			byte[] toWrite = new byte[in.available()];
			in.read(toWrite);
			hadoop.getFS().delete(statPath, true);
			
			int filesNum= Arrays.asList(hadoop.getFS().listStatus(new Path(hadoop.workingDirectory))).size();
			
			StringTokenizer tokens = new StringTokenizer(new String(toWrite), "'\n'");
			while(tokens.hasMoreElements()){
				
				StringTokenizer innerTokens = new StringTokenizer(new String(tokens.nextToken()), "'\t'");
				while(innerTokens.hasMoreElements()){
					Pair<String, Double> pair = new Pair<String, Double>();
					pair.setWord(innerTokens.nextToken());
					if(!innerTokens.hasMoreElements()){
						pair.setCount(Double.valueOf(Long.valueOf(pair.getWord())/filesNum));
						pair.setWord("");
					}
					retFrequency.add(pair);
				}
			}
			
		}
		
		return retFrequency;
	}
}
