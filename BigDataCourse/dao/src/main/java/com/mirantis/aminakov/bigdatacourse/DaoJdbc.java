package com.mirantis.aminakov.bigdatacourse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoJdbc {
	String driverName = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:";
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public DaoJdbc() throws InstantiationException, IllegalAccessException {
		try {
			Class.forName(driverName).newInstance();
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.err.println("Driver not found.");
			System.exit(0);
		}
		try {
			con = DriverManager.getConnection(url, "user", "password");
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	}
	public List<Book> getAllBooks(int pageNum, int pageSize) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		rs = st.executeQuery("SELECT * FROM books LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
		return books;
	}
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
		"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
				"WHERE Title = '" + title + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
		return books;
	}
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
		"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
				"WHERE Title = '" + author + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
		return books;
	}
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
		"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
				"WHERE Title = '" + genre + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
		return books;
	}	
}