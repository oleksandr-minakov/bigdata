package com.mirantis.aminakov.bigdatacourse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoJdbc implements Dao {
	String driverName = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:1234/bigdata";
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public DaoJdbc() throws InstantiationException, IllegalAccessException, DaoException {
		try {
			Class.forName(driverName).newInstance();
			try {
				con = DriverManager.getConnection(url, "aminakov", "bigdata");
			} catch (SQLException e) {
				throw new DaoException(e);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found.");
			System.exit(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.mirantis.aminakov.bigdatacourse.Dao#addBook(com.mirantis.aminakov.bigdatacourse.Book)
	 */
	@Override
	public int addBook(Book book) throws DaoException {
		int count = 0;
		try {
			con.setAutoCommit(false);
			pst = con.prepareStatement("INSERT INTO Books (title, book_id, author_id, genre_id) VALUES (?, ?, ?, ?);");
			pst.setString(1, book.getTitle());//
			rs = st.executeQuery("SELECT * From Author WHERE name ='" + book.getAuthor() + "');");
			if (rs.first()) {
				pst.setString(3, rs.getString("id"));
			} else {
				String author = null;
				//TODO algorithm adding author
				pst.setString(3, author);
			}
			
			
			st.addBatch("INSERT INTO Genres(genre) VALUES ('" + book.getGenre() + "')");
			st.addBatch("INSERT INTO Authors(author) VALUES ('" + book.getAuthor() + "')");
			st.executeBatch();
			String sql = "INSERT INTO Texts(text) VALUES ('?')";
			pst = con.prepareStatement(sql);
			pst.setAsciiStream(1, book.getText());
			count = pst.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
				throw new DaoException("Transaction filed " + e.getMessage());
			} catch (SQLException e1) {
				throw new DaoException("Rollback filed " + e1.getMessage());
			}
		} finally {
            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
            if (st != null) {
                try {
					st.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
            if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
			}
        }
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.mirantis.aminakov.bigdatacourse.Dao#getAllBooks(int, int)
	 */
	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books LIMIT" + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));	
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
            if (st != null) {
                try {
					st.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
        }
		return books;
	}
	
	/* (non-Javadoc)
	 * @see com.mirantis.aminakov.bigdatacourse.Dao#getBookByTitle(int, int, java.lang.String)
	 */
	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
					"WHERE Title = '" + title + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize); //TODO fix 
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));			
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
            if (st != null) {
                try {
					st.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
        }
		return books;
	}
	
	/* (non-Javadoc)
	 * @see com.mirantis.aminakov.bigdatacourse.Dao#getBookByAuthor(int, int, java.lang.String)
	 */
	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.id JOIN Texts ON Books.IdBook=Texts.IdBook " + 
					"WHERE Authors.name = '" + author + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize); //TODO fix 
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));			
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
            if (st != null) {
                try {
					st.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
        }
		return books;
	}
	
	/* (non-Javadoc)
	 * @see com.mirantis.aminakov.bigdatacourse.Dao#getBookByGenre(int, int, java.lang.String)
	 */
	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.IdGenre JOIN Texts ON Books.IdBook=Texts.IdBook " + 
					"WHERE Genres.genre = '" + genre + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize); //TODO fix 
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));			
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
            if (st != null) {
                try {
					st.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
        }
		return books;
	}	
	
	/* (non-Javadoc)
	 * @see com.mirantis.aminakov.bigdatacourse.Dao#getAuthorByGenre(int, int, java.lang.String)
	 */
	@Override
	public List<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws DaoException {
		List<String> authors = new ArrayList<String>();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT Authors.name FROM Books JOIN Authors ON Books.IdAuthor=Authors.IdAuthor " + 
					"JOIN Genres ON Books.IdGenre=Genres.IdGenre " + 
					"WHERE Genres.genre = '" + genre + "' LIMIT" + (pageNum-1) * pageSize  + "," + pageSize); //TODO fix 
			while (rs.next()) {
				String name = new String();
				name = rs.getString("author");
				authors.add(name);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
            if (st != null) {
                try {
					st.close();
				} catch (SQLException e) {
					throw new DaoException(e);
				}
            }
        }
		return authors;
	}

}