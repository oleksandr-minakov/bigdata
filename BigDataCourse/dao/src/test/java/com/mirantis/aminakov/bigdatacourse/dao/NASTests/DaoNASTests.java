package com.mirantis.aminakov.bigdatacourse.dao.NASTests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import com.mirantis.aminakov.bigdatacourse.dao.NAS.NASMapping;

public class DaoNASTests {
	
	public NASMapping nas;
	public String directory = "bookshelf/";
	
	public void setUp() throws DaoException{
		
		nas = new NASMapping(directory, 3);

	}
	
	public void cleanUp() throws IOException{
		
		Runtime.getRuntime().exec("rm -R " + directory);
	}
	
	@Test
	public void getHashByIDTest() throws DaoException, IOException{
		
		setUp();
		for(int i = 0; i <= 100; ++i){
			System.out.println(nas.getAbsolutePath(i));
		}
		cleanUp();
	}
	
	@SuppressWarnings("unused")
	@Test
	public void CrDelTest() throws DaoException, IOException{
		
		setUp();
		
		for(int i = 0; i <= 100; ++i){
			int res = nas.createDirectoryRecursively(i);
		}
		Assert.assertEquals(nas.getDirectory().list().length, 100);
		
		cleanUp();
	}
	
	@Test
	public void addFileTest() throws DaoException, IOException{
			
		setUp();
		
		InputStream is = new FileInputStream("src/test/java/testbook");
		int id = 100;
		nas.writeFile(id, is);
		
		cleanUp();
	}
	
	@Test
	public void removeFileTest() throws DaoException, IOException{
		
		setUp();
		
		InputStream is = new FileInputStream("src/test/java/testbook");
		int id = 100;
		int res1 = nas.writeFile(id, is);
		int res2 = nas.removeFile(id);
		cleanUp();
		
		Assert.assertEquals(res1, res2);

	}
	
	@Test
	public void readFileTest() throws DaoException, IOException{

		setUp();
		
		InputStream in = new FileInputStream("src/test/java/testbook");
		int id = 100;
		nas.writeFile(id, in);
		InputStream out = nas.readFile(id);
		
		Assert.assertNotEquals(out, null);
		cleanUp();
		
	}
	
}
