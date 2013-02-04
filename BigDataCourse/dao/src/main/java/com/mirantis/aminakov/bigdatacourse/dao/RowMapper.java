package com.mirantis.aminakov.bigdatacourse.dao;

import java.sql.*;

public interface RowMapper {
	Object mapRow(ResultSet rs, int row) throws SQLException;
}
