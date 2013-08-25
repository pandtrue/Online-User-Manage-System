package com.tianyi.service;
import java.sql.*;
import java.util.ArrayList;

import com.tianyi.domain.User;
import com.tianyi.util.SqlHelper;

public class UsersService {
	
	
	// Check whether user is valid or not
	public boolean checkUser(User u) {
		boolean res = false;
		
		//Use SQLHelper here to check validation
		String sql = "Select * from users where id=? and passwd=?";
		String[] paras ={String.valueOf(u.getId()), u.getPwd()};
		ArrayList al = SqlHelper.executeQuery(sql, paras);
		if(al.size()>0) {
			res = true;
		}
		return res;
	}
	
	// Get user based on page number
	// Why return arraylist but resultset?
	// 1. arraylist that contains user object is obey to OOP
	// 2. ResultSet -> User -> Arraylist, make arraylist irrelevant 
	// to resultset so we can close database resource 
	public ArrayList getUserByPage(int currentPage, int pageSize) {
		ArrayList al = new ArrayList();
		
		String sql = "select top " + pageSize + " * from users where id not in" +
				" (select top " + pageSize*(currentPage-1) + " id from users order by id)";
		al = SqlHelper.executeQuery(sql, null);
		return al;
	}
	
	// Get user by id
	public User getUserById(String id) {
		String sql = "Select * from users where id=?";
		String[] parameters={id};
		User user = SqlHelper.executeQuery(sql, parameters).get(0);
		return user;
	}
	
	// Get user by exact name
	public ArrayList getUserByExactName(String name) {
		String sql = "Select * from users where username=?";
		String[] parameters={name};
		ArrayList<User> user = SqlHelper.executeQuery(sql, parameters);
		return user;
	}
	
	// Get user by fuzzy name
	public ArrayList getUserByFuzzyName(String name) {
		String sql = "select * from users where username like ?";
		//String sql = "select * from users where username like '%"+name+"%'";
		String[] parameters={"%"+name+"%"};
		ArrayList<User> user = SqlHelper.executeQuery(sql, parameters);
		return user;
	}
	
	// Get page Count
	public int getPageCount(int pageSize) {
		String sql = "Select * from users";
		int rowCount = SqlHelper.executeQuery(sql, null).size();
		return rowCount%pageSize==0 ? rowCount/pageSize : rowCount/pageSize + 1;
	}
	
	public boolean deleteUser(int id) {
		String sql = "delete from users where id=?";
		String[] parameters = {String.valueOf(id)};
		return SqlHelper.executeUpdate(sql,parameters);
	}
	
	public boolean updateUser(User user) {
		String sql = "Update users set username=?, passwd=?, email=?, userLevel=? where id=? ";
		String[] parameters = {user.getName(), user.getPwd(), user.getEmail(), String.valueOf(user.getLvl()), String.valueOf(user.getId())};
		return SqlHelper.executeUpdate(sql,parameters);
	}
	
	public boolean addUser(User user) {
		String sql = "insert into users(username,passwd,email) values(?,?,?)";
		String[] parameters = {user.getName(), user.getPwd(), user.getEmail()};
		return SqlHelper.executeUpdate(sql,parameters);
	}
}


	

