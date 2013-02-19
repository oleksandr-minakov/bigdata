package com.mirantis.aminakov.bigdatacourse.dao1;

public class Constants {

	private static class ConstantsHolder{
		
		private static final Constants newInstanceHolder = new Constants();
	}
	
	public static Constants getInstance(){
		
		return ConstantsHolder.newInstanceHolder;
	}
	
	public static final String CLUSTER_NAME = "Test Cluster";
	public static final String KEYSPACE_NAME = "Bookshelf";
	public static final String CF_NAME = "Books";
	public static final String HOST_DEF = "localhost";
}
