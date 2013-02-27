package com.mirantis.aminakov.bigdatacourse.dao;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class DaoApp {
	public static final Logger LOG = Logger.getLogger(DaoApp.class);
	
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
            // TODO Auto-generated catch block
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        for (Book book1 : books) {
            System.out.println(book1.getTitle() + book1.getAuthor() + book1.getGenre() + book1.getText());
        }
    }
}
