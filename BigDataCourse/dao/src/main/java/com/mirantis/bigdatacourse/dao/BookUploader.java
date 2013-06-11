package com.mirantis.bigdatacourse.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BookUploader {
	
	private String baseDirectory;
	private Dao dao;
	
	public BookUploader(Dao dao, String baseDirectory) {
		super();
		this.dao = dao;
		this.baseDirectory = baseDirectory;
	}

	public int bookUploader() throws DaoException {
		
		File directory = new File(this.baseDirectory);
        File[] files;
        InputStream text;
        int listLength = 0;
		
		if(!directory.exists()) {
            throw new DaoException("Directory '" + baseDirectory + "'  with books for upload not exists.");
		} else {
			files = directory.listFiles();
            if (files != null) {
                listLength = files.length;
            }

			if(files != null && listLength == 0)
				throw new DaoException("No books in the directory '" + baseDirectory + "'.");

            for(int i = 0; i < listLength; ++i) {
				Book book = new Book();
                try {
                    text = new FileInputStream(files[i]);
                } catch (FileNotFoundException e) {
                    throw new DaoException("Read error: " + e.getMessage());
                }
                book.newBook("title" + i, "author" + i, "genre" + i, text);
				dao.addBook(book);
			}
			return listLength;
		}
	}
}
