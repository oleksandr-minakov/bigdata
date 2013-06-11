package com.mirantis.bigdatacourse.dao.NASTests;

import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.NAS.NASMapping;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DaoNASTests {
	
	public NASMapping nas;
	public String directory = "bookshelf/";
	
	public void setUp() throws DaoException {
		nas = new NASMapping(directory, 3);
	}
	
	public void cleanUp() throws IOException {
		Runtime.getRuntime().exec("rm -R " + directory);
	}
	
	@Test
	public void getHashByIDTest() throws DaoException, IOException {
		
		setUp();
		for(int i = 0; i <= 10; ++i){
			System.out.println(nas.getAbsolutePath(String.valueOf(i)));
		}
		cleanUp();
	}
	@SuppressWarnings("unused")
	@Test
	public void CrDelTest() throws DaoException, IOException {
		setUp();
		for(int i = 0; i <= 100; ++i){
			int res = nas.createDirectoryRecursively(String.valueOf(i));
		}
		Assert.assertEquals(nas.getDirectory().list().length, 100);
		cleanUp();
	}

	@SuppressWarnings("unused")
	@Test
	public void addFileTest() throws DaoException, IOException {
		setUp();
		InputStream is = new FileInputStream("testbook");
		int id = 100;
		nas.writeFile(String.valueOf(100), is);
		cleanUp();
	}


	@Test
	public void removeFileTest() throws DaoException, IOException {
		setUp();
		InputStream is;
		int res1, res2;
		for(int i = 1; i <= 100; ++i){
			is = new FileInputStream("testbook");
			res1 = nas.writeFile(String.valueOf(i), is);
			is.close();
		}
		for(int i = 1; i <= 100; ++i){
			res2 = nas.removeFile(String.valueOf(i));
		}
		cleanUp();
	}

	@Test
	public void readFileTest() throws DaoException, IOException {
		setUp();
		InputStream in = new FileInputStream("testbook");
		int id = 100;
		nas.writeFile(String.valueOf(id), in);
		InputStream out = nas.readFile(String.valueOf(id));
		Assert.assertNotEquals(out, null);
		cleanUp();
	}
}
