package com.mirantis.aminakov.bigdatacourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int row) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("book_id"));
		book.setTitle(rs.getString("title"));
		book.setAuthor(rs.getString("author"));
		book.setGenre(rs.getString("genre"));
		book.setText(rs.getAsciiStream("text"));
		return book;
	}

}
