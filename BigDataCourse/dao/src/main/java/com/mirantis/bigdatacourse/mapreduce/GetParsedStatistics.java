package com.mirantis.bigdatacourse.mapreduce;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.HadoopConnector;
import com.mirantis.bigdatacourse.dao.hadoop.configuration.Pair;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class GetParsedStatistics {
	
	public static final Logger LOG = Logger.getLogger(GetParsedStatistics.class);
	private HadoopConnector hadoop;
	
	public GetParsedStatistics(HadoopConnector hadoop){
		
			this.hadoop = hadoop;
	}
	
	public List<Pair<String, String>> getParsedStatistics(Path statPath) throws IOException, DaoException{
		
		List<Pair<String, String>> retFrequency = new ArrayList<Pair<String, String>>();
		
		Pair<String, String> nullOne = new Pair<String, String>();
			nullOne.setWord(" ");
			nullOne.setCount(String.valueOf(0));
		
		if(statPath.equals(new Path("/")))
			return retFrequency;
		
		if(!this.hadoop.getFS().exists(statPath))
			return retFrequency;
		
		List<FileStatus> fsList = Arrays.asList(hadoop.getFS().listStatus(statPath));
		LOG.debug("Checking file statistics for existance");
		if(fsList.size() >= 2){
			
			int count = Arrays.asList(hadoop.getFS().listStatus(new Path(hadoop.workingDirectory))).size();
			FSDataInputStream in = hadoop.getFS().open(fsList.get(1).getPath());
			LOG.debug("Getting statistics file");
			byte[] toWrite = new byte[in.available()];
			in.read(toWrite);
			StringTokenizer tokens = new StringTokenizer(new String(toWrite), "''*+-/|�{}'\n';:#$%&()"+ '"');
			LOG.debug("Parsing file ...");
			while(tokens.hasMoreElements()){
				
				StringTokenizer innerTokens = new StringTokenizer(new String(tokens.nextToken()), "' \t'");
				while(innerTokens.hasMoreElements()){
					
					Pair<String, String> pair = new Pair<String, String>();
					
					pair.setWord(innerTokens.nextToken());
					
					if(innerTokens.hasMoreElements() == false){
						
							if(retFrequency.indexOf(nullOne) != -1) {
							pair.setCount(String.valueOf(0));
							pair.setWord(" ");
							retFrequency.add(pair);
						}
					}
					else {
						pair.setCount(innerTokens.nextToken() + "/" + String.valueOf(count));
						retFrequency.add(pair);
					}
				}
			}
			LOG.debug("Parsing of statistics file was finished");
			in.close();
			LOG.debug("Closing file stream");
		}
		
		return retFrequency;
	}
}