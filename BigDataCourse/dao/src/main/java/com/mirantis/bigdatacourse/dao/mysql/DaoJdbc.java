package com.mirantis.bigdatacourse.dao.mysql;

import com.mirantis.bigdatacourse.dao.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DaoJdbc implements Dao {

    @Autowired
    private DataSource dataSource;

    protected Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    private int numberOfRecords = -1;
    public static final Logger LOG = Logger.getLogger(DaoJdbc.class);

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        LOG.debug("Set dataSource");
    }

    @Override
	public int addBook(Book book) throws DaoException {
        ManagementTables mt = new ManagementTables(dataSource);
        mt.createTables();
        mt.closeConnection();
        try {
            con = dataSource.getConnection();
			con.setAutoCommit(false);
			st = con.createStatement();
			int book_id = 0;
			rs = st.executeQuery("SELECT * FROM Books WHERE title = '" + book.getTitle() + "';");
			if (rs.first()) {
				con.rollback();
                LOG.debug("Book already exist");
				throw new BookAlreadyExists("Book already exists.");
			}
			String sql = "INSERT INTO Texts(text) VALUES (?)";
			pst = con.prepareStatement(sql);
			pst.setBlob(1, book.getText());
			pst.executeUpdate();
			rs = st.executeQuery("SELECT LAST_INSERT_ID();");
			while (rs.next()) {
				book_id = rs.getInt("LAST_INSERT_ID()");
			}
			pst.clearParameters();
			pst = null;
			pst = con.prepareStatement("INSERT INTO Books (title, book_id, author_id, genre_id) VALUES (?, ?, ?, ?);");
			pst.setString(1, book.getTitle());
			pst.setInt(2, book_id);
			rs = st.executeQuery("SELECT * FROM Authors WHERE author = '" + book.getAuthor() + "';");
			if (rs.first()) {
				pst.setString(3, rs.getString("id"));
			} else {
				int author_id = 0;
				st.executeUpdate("INSERT INTO Authors(author) VALUES ('" + book.getAuthor() + "');");
				rs = st.executeQuery("SELECT LAST_INSERT_ID();");
				while (rs.next()) {
					author_id = rs.getInt("LAST_INSERT_ID()");
				}
				pst.setInt(3, author_id);
			}
			rs = st.executeQuery("SELECT * FROM Genres WHERE genre = '" + book.getGenre() + "';");
			if (rs.first()) {
				pst.setString(4, rs.getString("id"));
			} else {
				int genre_id = 0;
				st.executeUpdate("INSERT INTO Genres(genre) VALUES ('" + book.getGenre() + "');");
				rs = st.executeQuery("SELECT LAST_INSERT_ID();");
				while (rs.next()) {
					genre_id = rs.getInt("LAST_INSERT_ID()");
				}
				pst.setInt(4, genre_id);
			}
			pst.executeUpdate();
			pst.clearParameters();
			con.commit();
			con.setAutoCommit(true);
            LOG.info("Add book.");
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
		return 0;
	}

	@Override
	public int delBook(String id) throws DaoException {
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			int count = st.executeUpdate("DELETE Books, Texts FROM Books, Texts WHERE Books.id = " + id + " AND Books.book_id = Texts.id;");
			if (count == 0) {
                LOG.debug("Book doesn't exist. Id = " + id);
                throw new DeleteException("Book doesn't exist.");
			}
            LOG.info("Delete book. Id = " + id);
        } catch(SQLException | DeleteException e) {
			throw new DeleteException("Can't delete book.");
        }
		return 0;
	}

	@Override
	public List<Book> getAllBooks(int pageNum, int pageSize) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
					"JOIN Genres ON Books.genre_id=Genres.id " +
					"JOIN Texts ON Books.book_id=Texts.id LIMIT " + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));	
			}
            LOG.info("Get all books");
            rs = st.executeQuery("SELECT FOUND_ROWS();");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
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

	@Override
	public List<Book> getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM Books JOIN Authors ON Books.author_id=Authors.id " + 
					"JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " + 
					"WHERE title LIKE '%" + title + "%' LIMIT " + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));			
			}
            LOG.info("Get book by title -> " + title);
            rs = st.executeQuery("SELECT FOUND_ROWS();");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
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

	@Override
	public List<Book> getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
					"JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " + 
					"WHERE author LIKE '%" + author + "%' LIMIT " + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));			
			}
            LOG.info("Get book by author -> " + author);
            rs = st.executeQuery("SELECT FOUND_ROWS();");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
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

	@Override
	public List<Book> getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM Books JOIN Authors ON Books.author_id=Authors.id " + 
					"JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " + 
					"WHERE genre LIKE '%" + genre + "%' LIMIT " + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));			
			}
            LOG.info("Get book by genre -> " + genre);
			rs = st.executeQuery("SELECT FOUND_ROWS();");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
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

	@Override
	public TreeSet<String> getAuthorByGenre(int pageNum, int pageSize, String genre) throws DaoException {
		TreeSet<String> authors = new TreeSet<String>();
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT SQL_CALC_FOUND_ROWS author FROM Books JOIN Authors ON Books.author_id=Authors.id " + 
					"JOIN Genres ON Books.genre_id=Genres.id " + 
					"WHERE genre LIKE '%" + genre + "%' LIMIT " + (pageNum-1) * pageSize  + "," + pageSize);
			while (rs.next()) {
				String name = new String();
				name = rs.getString("author");
				authors.add(name);
			}
            LOG.info("Get author by genre -> " + genre);
			rs = st.executeQuery("SELECT FOUND_ROWS();");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
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

	@Override
	public List<Book> getBookByText(int pageNum, int pageSize, String text) throws DaoException {
		List<Book> books = new ArrayList<Book>();
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM Books JOIN Authors ON Books.author_id=Authors.id " + 
					"JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " + 
					"WHERE text LIKE '%" + text + "%' LIMIT " + (pageNum-1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book)map.mapRow(rs, 0));			
			}
            LOG.info("Get book by text -> " + text);
			rs = st.executeQuery("SELECT FOUND_ROWS();");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
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

	@Override
	public void closeConnection() throws DaoException {
        LOG.debug("Close connection");
        if (this.con != null) {
			try {
				this.con.close();
			} catch (SQLException e) {
				throw new DaoException(e);
			}
		}
    }

	@Override
	public int getNumberOfRecords() {
        LOG.debug("Get number of records");
        return numberOfRecords;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
        LOG.debug("IN SECTION afterPropertiesSet");
        if(this.dataSource == null) {
            throw new DaoException("Error with MySQL bean initialization");
        }
		
	}

	@Override
	public void destroy() throws DaoException {
        LOG.debug("IN SECTION destroy");
        closeConnection();
	}
}
