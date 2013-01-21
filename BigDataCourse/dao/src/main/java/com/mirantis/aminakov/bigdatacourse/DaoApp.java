package com.mirantis.aminakov.bigdatacourse;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
//import java.util.TreeSet;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class DaoApp 
{
	public static final Logger LOG = Logger.getLogger(DaoApp.class);
	
    public static void main( String[] args )
    {
    	/*try {
			Dao dao = new DaoJdbc();
			List<Book> books = new ArrayList<Book>();
			books = dao.getBookByGenre(1, 10, "Хокку");
			for (Book book : books) {
				System.out.println(book.getTitle());
				System.out.println(book.getAuthor());
				System.out.println(book.getGenre());
				byte[] buf = new byte[30];
				try {
					int count = book.getText().read(buf, 0, 30);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(new String(buf));
			}
		} catch (InstantiationException | IllegalAccessException | DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	DOMConfigurator.configure("log4j.xml");
    	System.out.println( "Hello World! От дао..." );
    	LOG.debug("Print string <Hello world!>.");
    	List<Book> books = new ArrayList<Book>();
    	BookGenerator gen = new BookGenerator();
    	books = gen.generateBooks();
    	for (Book book : books) {
			System.out.println(book.getTitle() + " " + book.getAuthor() + " " + book.getGenre() + " " + book.getText().toString());
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
