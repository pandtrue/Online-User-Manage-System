package com.tianyi.util;
import java.sql.*;
import java.util.ArrayList;

import com.tianyi.domain.User;

/**
 * Class use for access and control the database
 */
public class SqlHelper {
	//Define things needed by SQL
	private static PreparedStatement ps = null;
	private static Connection ct = null;
	private static ResultSet rs = null;

	public static ArrayList<User> executeQuery(String sql, String[] paras) {
		ArrayList<User> res = new ArrayList<User>();
		try {
			ct = DBUtil.getConnection();
			ps = ct.prepareStatement(sql);
			if(paras!=null) {
				for (int i=0;i<paras.length;i++) {
					ps.setString(i+1, paras[i]);
				}
			}
			if(ps.executeQuery()==null){
				return null;
			}
			rs = ps.executeQuery();
			// User RsultSetMetaData here, it is very useful
			ResultSetMetaData rsmd = rs.getMetaData();
			// Get number of cols
			int colNum = rsmd.getColumnCount();
			// Get data from resultset and encapsulate into arraylist
			User u = null;
			while(rs.next()){
				u = new User();
				u.setId(rs.getInt(1));
				u.setName(rs.getString(2));
				u.setPwd(rs.getString(3));
				u.setEmail(rs.getString(4));
				u.setLvl(rs.getInt(5));
				res.add(u);
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getLocalizedMessage());
		} finally {
			DBUtil.close(rs, ps, ct);
		}
	}
	
	public static boolean executeUpdate(String sql, String[] paras) {
		Statement st = null;
		boolean res = true;
		try {
			ct = DBUtil.getConnection();
			ps = ct.prepareStatement(sql);
			if(paras!=null) {
				for (int i=0;i<paras.length;i++) {
					ps.setString(i+1, paras[i]);
				}
			}
			ps.executeUpdate();
		} catch (Exception e) {
			res = false;
			e.printStackTrace();
		}
		return res;
	}
}
