package com.mirantis.aminakov.bigdatacourse;

import java.sql.*;

public interface RowMapper {
	Object mapRow(ResultSet rs, int row) throws SQLException;
}
