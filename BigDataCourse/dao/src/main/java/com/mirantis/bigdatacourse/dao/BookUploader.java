package com.mirantis.bigdatacourse.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BookUploader {
	
	private String stringBaseDir;
	private Dao service;
	private int id = 1;
	
	public BookUploader(Dao service, String stringBaseDir, int id) {
		super();
		this.service = service;
		this.stringBaseDir = stringBaseDir;
		this.id = id;
	}

	public int bookUploder() throws IOException, DaoException{
		
		File baseDir = new File(this.stringBaseDir);
		
		if(!baseDir.exists()){
			baseDir.mkdirs();
			return 0;
		}
		else{
			
			File[] files = baseDir.listFiles();
			if(files.length == 0)
				return 0;
			for(int i = 0; i < files.length; ++i){
				
				Book book = new Book();
				book.setId(this.id);
				book.newBook("title"+i, "author"+i, "genre"+i, new FileInputStream(files[i]));
				service.addBook(book);
				this.id++;
			}
			
			return files.length;
		}
	}

}
