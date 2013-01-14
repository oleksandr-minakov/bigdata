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
	String url = "jdbc:mysql://localhost:1234/bigdata";
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public DaoJdbc() throws InstantiationException, IllegalAccessException {
		try {
			Class.forName(driverName).newInstance();
			try {
				con = DriverManager.getConnection(url, "aminakov", "bigdata");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.err.println("Driver not found.");
			System.exit(0);
		}
	}
	public List<Book> getAllBooks(int pageNum, int pageSize) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM books LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));	// intRow ???	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
		return books;
	}
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
					"WHERE Title = '" + title + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));	// intRow ???			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
		return books;
	}
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
					"WHERE Authors.author = '" + author + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));	// intRow ???			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
		return books;
	}
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
					"WHERE Genres.genre = '" + genre + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));	// intRow ???			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
		return books;
	}	
	public List<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws SQLException {
		List<String> authors = new ArrayList<String>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT Authors.name FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.IdGenre " + 
					"WHERE Genres.genre = '" + genre + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
			while (rs.next()) {
				String name = new String();
				name = rs.getString("author");
				authors.add(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
		return authors;
	}	
}