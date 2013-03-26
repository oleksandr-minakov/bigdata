package com.mirantis.aminakov.bigdatacourse.dao.hadoop.configuration;

public class Pair<K, V> {

	private K word;
	private V count;
	
	public K getWord(){
		 return this.word;
	}
	
	public V getCount(){
		 return this.count;
	}
	
	public void setWord(K word){
		 this.word = word;
	}
	
	public void setCount(V count){
		 this.count = count;
	}
}
