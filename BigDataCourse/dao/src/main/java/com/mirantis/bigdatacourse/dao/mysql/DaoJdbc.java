package com.mirantis.bigdatacourse.dao.mysql;

import com.mirantis.bigdatacourse.dao.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoJdbc implements Dao {

    @Value("#{properties.mysql_driver}")
    private String driverClassName;

    @Value("#{properties.mysql_url}")
    private String url;

    @Value("#{properties.mysql_username}")
    private String username;

    @Value("#{properties.mysql_password}")
    private String password;

    DataSource dataSource;

    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement pst;
    public static final Logger LOG = Logger.getLogger(DaoJdbc.class);

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        LOG.debug("Set dataSource");
    }

    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        if (dataSource == null) {
            ds.setDriverClassName(driverClassName);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            setDataSource(ds);
        }
        return dataSource;
    }

    @Override
	public int addBook(Book book) throws DaoException {
        getDataSource();
        ManagementTables mt = new ManagementTables(dataSource);
        mt.createTables();
        mt.closeConnection();
        try {
            con = dataSource.getConnection();
			con.setAutoCommit(false);
			st = con.createStatement();
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
				book.setId(rs.getString("LAST_INSERT_ID()"));
			}
			pst.clearParameters();
			pst = null;
			pst = con.prepareStatement("INSERT INTO Books (title, book_id, author_id, genre_id) VALUES (?, ?, ?, ?);");
			pst.setString(1, book.getTitle());
			pst.setInt(2, Integer.parseInt(book.getId()));
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
            LOG.info("Add book." + book.getId());
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
        getDataSource();
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
	public PaginationModel getAllBooks(int pageNum, int pageSize) throws DaoException {
        getDataSource();
        List<Book> books = new ArrayList<Book>();
        PaginationModel model = new PaginationModel();
        int numberOfRecords = 0;
		try {
            con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
					"JOIN Genres ON Books.genre_id=Genres.id " +
					"JOIN Texts ON Books.book_id=Texts.id LIMIT " + (pageNum - 1) * pageSize  + "," + pageSize);
			BookMapper map = new BookMapper();
			while (rs.next()) {
				books.add((Book) map.mapRow(rs, 0));
			}
            LOG.info("Get all books");
            rs = st.executeQuery("SELECT COUNT(*) FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id " +
                    "JOIN Texts ON Books.book_id=Texts.id;");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
            }
            LOG.info("Get number of records (getAllBooks) -> " + numberOfRecords);
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
        model.setBooks(books);
        model.setNumberOfRecords(numberOfRecords);
		return model;
	}

	@Override
	public PaginationModel getBookByTitle(int pageNum, int pageSize, String title) throws DaoException {
        getDataSource();
        List<Book> books = new ArrayList<Book>();
        PaginationModel model = new PaginationModel();
        int numberOfRecords = 0;
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE title LIKE '%" + title + "%' LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
            BookMapper map = new BookMapper();
            while (rs.next()) {
                books.add((Book) map.mapRow(rs, 0));
            }
            LOG.info("Get book by title -> " + title);
            rs = st.executeQuery("SELECT COUNT(*) FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE title LIKE '%" + title + "%';");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
            }
            LOG.info("Get number of records (getBookByTitle) -> " + numberOfRecords);
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
        model.setBooks(books);
        model.setNumberOfRecords(numberOfRecords);
        return model;
    }

	@Override
	public PaginationModel getBookByAuthor(int pageNum, int pageSize, String author) throws DaoException {
        getDataSource();
        List<Book> books = new ArrayList<Book>();
        PaginationModel model = new PaginationModel();
        int numberOfRecords = 0;
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE author LIKE '%" + author + "%' LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
            BookMapper map = new BookMapper();
            while (rs.next()) {
                books.add((Book) map.mapRow(rs, 0));
            }
            LOG.info("Get book by author -> " + author);
            rs = st.executeQuery("SELECT COUNT(*) FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE author LIKE '%" + author + "%';");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
            }
            LOG.info("Get number of records (getBookByAuthor) -> " + numberOfRecords);
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
        model.setBooks(books);
        model.setNumberOfRecords(numberOfRecords);
        return model;
    }

	@Override
	public PaginationModel getBookByGenre(int pageNum, int pageSize, String genre) throws DaoException {
        getDataSource();
        List<Book> books = new ArrayList<Book>();
        PaginationModel model = new PaginationModel();
        int numberOfRecords = 0;
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE genre LIKE '%" + genre + "%' LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
            BookMapper map = new BookMapper();
            while (rs.next()) {
                books.add((Book) map.mapRow(rs, 0));
            }
            LOG.info("Get book by genre -> " + genre);
            rs = st.executeQuery("SELECT  COUNT(*) FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE genre LIKE '%" + genre + "%';");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
            }
            LOG.info("Get number of records (getBookByGenre) -> " + numberOfRecords);
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
        model.setBooks(books);
        model.setNumberOfRecords(numberOfRecords);
        return model;
    }

	@Override
	public PaginationModel getBookByText(int pageNum, int pageSize, String text) throws DaoException {
        getDataSource();
        List<Book> books = new ArrayList<Book>();
        PaginationModel model = new PaginationModel();
        int numberOfRecords = 0;
        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE text LIKE '%" + text + "%' LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
            BookMapper map = new BookMapper();
            while (rs.next()) {
                books.add((Book) map.mapRow(rs, 0));
            }
            LOG.info("Get book by text -> " + text);
            rs = st.executeQuery("SELECT * FROM Books JOIN Authors ON Books.author_id=Authors.id " +
                    "JOIN Genres ON Books.genre_id=Genres.id JOIN Texts ON Books.book_id=Texts.id " +
                    "WHERE text LIKE '%" + text + "%';");
            while (rs.next()) {
                numberOfRecords = rs.getInt(1);
            }
            LOG.info("Get number of records (getBookByText) -> " + numberOfRecords);
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
        model.setBooks(books);
        model.setNumberOfRecords(numberOfRecords);
        return model;
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
	public void afterPropertiesSet() throws Exception {
        LOG.debug("IN SECTION afterPropertiesSet");
       /* if(this.dataSource == null) {
            throw new DaoException("Error with MySQL bean initialization");
        }*/
	}

	@Override
	public void destroy() throws DaoException {
        LOG.debug("IN SECTION destroy");
        closeConnection();
	}
}
