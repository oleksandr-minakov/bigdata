package com.mirantis.bigdatacourse.dao;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyGeneratorTest {
	
	KeyGenerator idGen = new KeyGenerator();

	@Test
	public void getNewID() throws DaoException {
		
		
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(Thread.activeCount()));
		list.add(Thread.currentThread().getName());
		list.add(Thread.currentThread().toString());
		list.add(Thread.currentThread().getState().toString());
		
		for(int i = 0; i < 100; i++) {
			
			Random rnd = new Random(i);
			list.add(String.valueOf(i));
			list.add(String.valueOf(rnd.nextLong()));
			String id = idGen.getNewID(list);
			System.out.println("New Id is - "+id);

		}
		
	}
	
}
