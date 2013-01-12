package com.mirantis.aminakov.bigdatacourse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoJdbc {
	String driverName = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:";
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public DaoJdbc(Book[] books, String findString) throws InstantiationException, IllegalAccessException {
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
			rs = st.executeQuery("SELECT * FROM BOOKS");
			while (rs.next()) {
				String str = rs.getString(1) + "::" + rs.getString(2);
				System.out.println(str);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
	}
}