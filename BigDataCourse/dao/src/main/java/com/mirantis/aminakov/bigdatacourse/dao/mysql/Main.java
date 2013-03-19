package com.mirantis.aminakov.bigdatacourse.dao.mysql;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.Dao;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static final Logger LOG = Logger.getLogger(Main.class);
	
    public static void main( String[] args ) {
    	System.out.println( "Hello World! From dao." );
    	LOG.debug("Print string <Hello world!>.");

        Dao dao;
        int counter;
        Book book = new Book();
        List<Book> books = new ArrayList<Book>();
        FileInputStream fis = null;
        File file_r = new File("file.txt");
        try {
            fis = new FileInputStream(file_r);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        book.setTitle("рлофыравфар");
        book.setAuthor("автор");
        book.setGenre("новый жанр");
        book.setText(fis);
        try {
            dao = new DaoJdbc();
            counter = dao.addBook(book);
            books = dao.getBookByAuthor(1, 10, "автор");
        } catch (DaoException e) {
            e.printStackTrace();
        }
        for (Book book1 : books) {
            System.out.println(book1.getTitle() + book1.getAuthor() + book1.getGenre() + book1.getText());
        }
    }
}
