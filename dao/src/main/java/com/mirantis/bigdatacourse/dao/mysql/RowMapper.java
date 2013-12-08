package com.mirantis.bigdatacourse.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
	Object mapRow(ResultSet rs, int row) throws SQLException;
}
