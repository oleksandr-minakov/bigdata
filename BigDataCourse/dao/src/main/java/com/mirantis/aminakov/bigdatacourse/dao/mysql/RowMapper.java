package com.mirantis.aminakov.bigdatacourse.dao.mysql;

import java.sql.*;

public interface RowMapper {
	Object mapRow(ResultSet rs, int row) throws SQLException;
}
