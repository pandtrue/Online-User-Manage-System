package com.tianyi.util;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
	//Define things needed by SQL
	private static PreparedStatement ps = null;
	private static Connection ct = null;
	private static ResultSet rs = null;
	
	private static String url = "";
	private static String username = "";
	private static String password = "";
	private static String driver = "";
	
	static {
		try {
			Properties p = new Properties();
			// For java web project, use classloader to read resource, default path is src folder
			InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("dbInfo.properties");
			p.load(is);
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			password = p.getProperty("password");
			username = p.getProperty("username");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			ct = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}
	
	public static void close(ResultSet rs, Statement ps, Connection ct) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs=null;
		}
		if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps=null;
		}
		if(ct!=null) {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ct=null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(driver);
		System.out.println(url);
		System.out.println(username);
		System.out.println(password);
	}
}
