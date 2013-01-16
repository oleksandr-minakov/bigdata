package com.mirantis.aminakov.bigdatacourse;

import java.util.TreeSet;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class DaoApp 
{
    public static void main( String[] args )
    {
    	try {
			Dao dao = new DaoJdbc();
			TreeSet<String> authors = new TreeSet<String>();
			authors = dao.getAuthorByGenre(1, 10, "Roman");
			for (String author : authors) {
				System.out.println(author);
			}
		} catch (InstantiationException | IllegalAccessException | DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
        /*System.out.println( "Hello World! От дао..." );
        FileInputStream fis = null;
        File file = new File("/home/aminakov/test4.txt");
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			Dao dao = new DaoJdbc();
			Book book = new Book();
			book.setAuthor("Сидоров А.А.");
			book.setGenre("Хокку");
			book.setTitle("Первый стих");
			book.setText(fis);
			dao.addBook(book);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println( "After book add..." );*/
    }
}
