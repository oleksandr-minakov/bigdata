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
	
	public List<Pair<String, Long>> getParsedStatistics(Path statPath) throws IOException, DaoException{
		
		List<FileStatus> fsList = Arrays.asList(hadoop.getFS().listStatus(statPath));
		List<Pair<String, Long>> ret = new ArrayList<Pair<String, Long>>();
		if(fsList.size() >= 2){
			
			FSDataInputStream in = hadoop.getFS().open(fsList.get(1).getPath());
			
			byte[] toWrite = new byte[in.available()];
			in.read(toWrite);
			hadoop.getFS().delete(statPath, true);
			
			StringTokenizer tokens = new StringTokenizer(new String(toWrite), "'\n'");
			while(tokens.hasMoreElements()){
				
				StringTokenizer innerTokens = new StringTokenizer(new String(tokens.nextToken()), "'\t'");
				while(innerTokens.hasMoreElements()){
					Pair<String, Long> pair = new Pair<String, Long>();
					pair.setWord(innerTokens.nextToken());
					pair.setCount(Long.valueOf(innerTokens.nextToken()));
					ret.add(pair);
				}
			}
			
		}
		
		return ret;
	}
}
